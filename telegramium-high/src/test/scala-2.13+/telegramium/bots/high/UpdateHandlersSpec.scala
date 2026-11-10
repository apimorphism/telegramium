package telegramium.bots.high

import scala.collection.mutable.ListBuffer

import cats.effect.IO
import cats.effect.unsafe.implicits.global

import io.circe.Json
import org.scalacheck.Gen
import org.scalacheck.rng.Seed
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import telegramium.bots.CirceImplicits.*
import telegramium.bots.GenPrims
import telegramium.bots.GenUpdate
import telegramium.bots.Update
import telegramium.bots.client.Method

/** Every field of the generated Update but updateId, i.e. every kind of update, reaches its handler in LongPollBot and
  * WebhookBot. A kind added to the Bot API fails here until the bots, and the test bots, handle it.
  */
class UpdateHandlersSpec extends AnyFreeSpec with Matchers {

  private val params = Gen.Parameters.default.withSize(GenPrims.Budget)

  // generated updates, cut down to update_id and one other field each, by that field's JSON key
  private val (undecoded, updates) =
    (0L until 100L).toList
      .flatMap(i => updateEncoder(GenUpdate.gen.pureApply(params, Seed(i))).asObject.toList.flatMap(_.toList))
      .collect { case (key, value) if key != "update_id" && !value.isNull => key -> value }
      .distinctBy(_._1)
      .partitionMap { case (key, value) =>
        Json.obj("update_id" -> Json.fromInt(1), key -> value).as[Update].left.map(e => key -> e)
      }

  // a failed test each, rather than the whole suite aborting
  undecoded.foreach { case (key, e) =>
    s"an update with only $key decodes" in fail(e.toString)
  }

  // the field an update sets, e.g. widgetQuery
  private def kindOf(update: Update): String =
    update.productElementNames.zip(update.productIterator).collectFirst { case (name, Some(_)) => name }.get

  private val kinds: Map[String, Update] = updates.groupBy(kindOf).map { case (kind, us) => kind -> us.head }

  "every kind of update is sampled" in {
    kinds.keySet shouldBe updates.head.productElementNames.toSet - "updateId"
  }

  // the test bots send a message named after the handler they're in
  private def textOf(method: Method[?]): String =
    method.payload.json.hcursor.get[String]("text").getOrElse(s"${method.payload.name} without text")

  private def recordingApi(sent: ListBuffer[String]): Api[IO] = new Api[IO] {
    override def execute[Res](method: Method[Res]): IO[Res] = IO(sent += textOf(method)).as(null.asInstanceOf[Res])
  }

  kinds.toList.sortBy(_._1).foreach { case (kind, update) =>
    val handler = "on" + kind.capitalize

    s"$kind reaches LongPollBot.$handler" in {
      val sent = ListBuffer.empty[String]
      new TestLongPollBot(recordingApi(sent)).onUpdate(update).unsafeRunSync()
      sent.toList shouldBe List(handler)
    }

    s"$kind reaches WebhookBot.$handler and ${handler}Reply" in {
      val sent  = ListBuffer.empty[String]
      val reply = new TestWebhookBot(recordingApi(sent)).onUpdate(update).unsafeRunSync()
      sent.toList shouldBe List(handler)
      reply.map(textOf) shouldBe Some(handler + "Reply")
    }
  }

}
