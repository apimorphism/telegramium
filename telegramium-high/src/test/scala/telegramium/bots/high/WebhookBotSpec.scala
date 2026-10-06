package telegramium.bots.high

import cats.effect.IO
import cats.effect.Ref
import cats.effect.unsafe.implicits.global
import cats.syntax.all.*

import io.circe.Json
import io.circe.parser.parse
import org.http4s.Method.POST
import org.http4s.Request
import org.http4s.Status
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.circe.*
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import telegramium.bots.Update
import telegramium.bots.client.Method

class WebhookBotSpec extends AnyFreeSpec with Matchers {

  // Accepts setWebhook, the only method the bot calls on start
  private val api: Api[IO] = new Api[IO] {
    override def execute[Res](method: Method[Res]): IO[Res] = IO.fromEither(method.decoder.decodeJson(Json.True))
  }

  private def post(update: String, unknownUpdates: Ref[IO, List[(Int, List[String])]]): IO[Status] = {
    val bot = new WebhookBot[IO](api, url = "localhost") {
      override def onUnknownUpdate(update: Update, unknownFields: List[String], json: Json): IO[Unit] =
        unknownUpdates.update((update.updateId, unknownFields) :: _)
    }
    (bot.start(0), BlazeClientBuilder[IO].resource).tupled.use { case (server, client) =>
      client.status(Request[IO](POST, server.baseUri).withEntity(parse(update).toOption.get))
    }
  }

  "should acknowledge an update of an unknown type and report it" in {
    val (status, unknownUpdates) = (for {
      unknownUpdates <- Ref.of[IO, List[(Int, List[String])]](List.empty)
      status         <- post("""{"update_id": 1, "some_future_update": {"id": 0}}""", unknownUpdates)
      reported       <- unknownUpdates.get
    } yield (status, reported)).unsafeRunSync()

    status shouldBe Status.Ok
    unknownUpdates shouldBe List(1 -> List("some_future_update"))
  }

  "should not report an update of a known type" in {
    val (status, unknownUpdates) = (for {
      unknownUpdates <- Ref.of[IO, List[(Int, List[String])]](List.empty)
      status         <- post(
        """{"update_id": 1, "message": {"message_id": 0, "date": 0, "chat": {"id": 0, "type": "private"}}}""",
        unknownUpdates
      )
      reported <- unknownUpdates.get
    } yield (status, reported)).unsafeRunSync()

    status shouldBe Status.Ok
    unknownUpdates shouldBe List.empty
  }

}
