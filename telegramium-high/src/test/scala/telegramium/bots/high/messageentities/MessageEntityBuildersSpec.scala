package telegramium.bots.high.messageentities

import org.scalacheck.Gen
import org.scalacheck.rng.Seed
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import telegramium.bots.GenPrims
import telegramium.bots.MessageEntity
import telegramium.bots.RoundTrips

/** Every variant of the generated MessageEntity has a builder: XMessageEntity(offset, length, rest*) is built by the
  * MessageEntityFormat.X(text, rest*) case class and by MessageEntities().x(text, rest*). The variants come from the
  * generated round trip cases, so a variant added to the Bot API fails here until the builders get it.
  */
class MessageEntityBuildersSpec extends AnyFreeSpec with Matchers {

  private val params = Gen.Parameters.default.withSize(GenPrims.Budget)

  // a sample of each variant; MessageEntity's own case, the sum of them, samples as one of the variants
  private val variants: List[Product] =
    RoundTrips.all
      .map(c => (c.name, c.gen.pureApply(params, Seed(0L))))
      .collect { case (name, e: Product) if e.isInstanceOf[MessageEntity] && e.getClass.getSimpleName == name => e }

  // the case classes' fields, offset and length first
  private def fields(entity: MessageEntity): List[Any] = entity.asInstanceOf[Product].productIterator.toList

  private val text = "abc"

  "MessageEntity has variants" in {
    variants should not be empty
  }

  variants.foreach { entity =>
    val name   = entity.getClass.getSimpleName.stripSuffix("MessageEntity")
    val rest   = entity.productIterator.drop(2).map(_.asInstanceOf[AnyRef]).toList
    val args   = text :: rest
    val result = entity.productIterator.toList match {
      case _ :: _ :: tail => 0 :: text.length :: tail
      case other          => other
    }

    name - {
      s"is built by MessageEntityFormat.$name" in {
        val format = (try Class.forName(classOf[MessageEntityFormat].getName + "$" + name)
        catch { case _: ClassNotFoundException => fail(s"MessageEntityFormat.$name doesn't exist") })
          .getConstructors
          .find(_.getParameterCount == args.size)
          .getOrElse(fail(s"MessageEntityFormat.$name doesn't take ${args.size} arguments"))
          .newInstance(args*)
          .asInstanceOf[MessageEntityFormat]
        val built = MessageEntityFormat.toMessageEntities(Vector(format))
        built.map(_.getClass) shouldBe List(entity.getClass)
        built.map(fields) shouldBe List(result)
      }

      val builder = name.head.toLower.toString + name.tail
      s"is built by MessageEntities().$builder" in {
        // a value class: its methods return the underlying Vector on the JVM
        val entities = MessageEntities(
          classOf[MessageEntities].getMethods
            .find(m => m.getName == builder && m.getParameterCount == args.size)
            .getOrElse(fail(s"MessageEntities has no $builder method taking ${args.size} arguments"))
            .invoke(MessageEntities(), args*)
            .asInstanceOf[Vector[MessageEntityFormat]]
        )
        entities.toPlainText() shouldBe text
        val built = entities.toTelegramEntities()
        built.map(_.getClass) shouldBe List(entity.getClass)
        built.map(fields) shouldBe List(result)
      }
    }
  }

}
