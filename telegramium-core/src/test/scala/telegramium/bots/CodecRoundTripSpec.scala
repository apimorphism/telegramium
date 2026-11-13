package telegramium.bots

import io.circe.parser.decode
import org.scalacheck.Prop
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import telegramium.bots.GenPrims.RoundTripCase

class CodecRoundTripSpec extends AnyFlatSpec with Matchers {

  private val seededCheck = new SeededCheck("ROUNDTRIP_SEED")

  private def roundTrips[A](c: RoundTripCase[A]): Unit =
    c.name should "survive encode → decode" in {
      // through the JSON text, as it goes over the wire
      val prop = Prop.forAllShrink(c.gen, c.shrink.shrink) { a =>
        val json = c.encoder(a).noSpaces
        decode(json)(c.decoder) match {
          case Right(decoded) => Prop(decoded == a) :| s"decoded to a different value: $decoded"
          case Left(error)    => Prop.falsified :| s"failed to decode $json: $error"
        }
      }
      seededCheck
        .failure(c.name, prop, a => s"$a\nAs JSON: ${c.encoder(a.asInstanceOf[A]).noSpaces}")
        .foreach(fail(_))
    }

  RoundTrips.all.foreach(c => roundTrips(c))

  "An unknown string" should "encode back to itself, next to a known one" in {
    import io.circe.syntax._
    import CirceImplicits._
    val dice = List(Dice(iozhik.OpenEnum.Unknown("🪀"), 3), Dice(iozhik.OpenEnum(EmojiDice), 6))
    dice.asJson.noSpaces shouldBe """[{"emoji":"🪀","value":3},{"emoji":"🎲","value":6}]"""
    decode[List[Dice]](dice.asJson.noSpaces) shouldBe Right(dice)
  }
}
