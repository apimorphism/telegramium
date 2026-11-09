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

/** Every kind of update, i.e. every field of the generated Update but update_id, reaches its own handler in LongPollBot
  * and WebhookBot. The kinds come from the generated Update's encoder, so a kind added to the Bot API fails here until
  * the bots dispatch it, and the test bots override its handlers.
  */
class UpdateHandlersSpec extends AnyFreeSpec with Matchers {

  // a value of each kind, as JSON, from updates sampled with fixed seeds until every field has shown up
  private val kinds: Map[String, Json] = {
    val params = Gen.Parameters.default.withSize(GenPrims.Budget)
    Iterator
      .from(0)
      .take(1000)
      .map(i => GenUpdate.gen.pureApply(params, Seed(i.toLong)))
      .scanLeft((0, Map.empty[String, Json])) { case ((_, acc), update) =>
        val fields = updateEncoder(update).asObject.get.toList.filter { case (k, v) => k != "update_id" && !v.isNull }
        (update.productArity - 1, acc ++ fields)
      }
      .collectFirst { case (arity, acc) if arity > 0 && acc.size == arity => acc }
      .getOrElse(fail("1000 sampled updates didn't set every field of Update"))
  }

  private def single(kind: String): Update =
    Json.obj("update_id" -> Json.fromInt(1), kind -> kinds(kind)).as[Update].fold(e => fail(s"$kind: $e"), identity)

  // the test bots send a message named after each handler: onWidgetQuery for widget_query
  private def handlerOf(kind: String): String = "on" + kind.split('_').map(_.capitalize).mkString

  private def textOf(method: Method[?]): String = method.payload.json.hcursor.get[String]("text").toOption.get

  private def recordingApi(sent: ListBuffer[String]): Api[IO] = new Api[IO] {
    override def execute[Res](method: Method[Res]): IO[Res] =
      IO(sent += textOf(method)).as(null.asInstanceOf[Res])
  }

  kinds.keys.toList.sorted.foreach { kind =>
    s"$kind" - {
      "is passed to its LongPollBot handler" in {
        val sent = ListBuffer.empty[String]
        new TestLongPollBot(recordingApi(sent)).onUpdate(single(kind)).unsafeRunSync()
        sent.toList shouldBe List(handlerOf(kind))
      }

      "is passed to its WebhookBot handler and reply handler" in {
        val sent  = ListBuffer.empty[String]
        val reply = new TestWebhookBot(recordingApi(sent)).onUpdate(single(kind)).unsafeRunSync()
        sent.toList shouldBe List(handlerOf(kind))
        reply.map(textOf) shouldBe Some(handlerOf(kind) + "Reply")
      }
    }
  }

}
