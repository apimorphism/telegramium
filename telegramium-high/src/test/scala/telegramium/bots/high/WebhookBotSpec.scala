package telegramium.bots.high

import java.io.ByteArrayInputStream

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

import telegramium.bots.ChatIntId
import telegramium.bots.InputStreamFile
import telegramium.bots.Message
import telegramium.bots.NamedInputStream
import telegramium.bots.Update
import telegramium.bots.client.Method
import telegramium.bots.client.Methods

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

  "should still dispatch an update's known fields even if onUnknownUpdate fails" in {
    val (status, messages) = (for {
      messages <- Ref.of[IO, List[String]](List.empty)
      bot = new WebhookBot[IO](api, url = "localhost") {
        override def onUnknownUpdate(update: Update, unknownFields: List[String], json: Json): IO[Unit] =
          IO.raiseError(new RuntimeException("reporting failed"))
        override def onMessage(msg: Message): IO[Unit] = messages.update(msg.text.mkString :: _)
      }
      status <- (bot.start(0), BlazeClientBuilder[IO].resource).tupled.use { case (server, client) =>
        client.status(
          Request[IO](POST, server.baseUri).withEntity(
            parse(
              """{"update_id": 1, "some_future_update": {"id": 0}, "message": {"message_id": 0, "date": 0,
                |"chat": {"id": 0, "type": "private"}, "text": "hi"}}""".stripMargin
            ).toOption.get
          )
        )
      }
      dispatched <- messages.get
    } yield (status, dispatched)).unsafeRunSync()

    status shouldBe Status.Ok
    messages shouldBe List("hi")
  }

  "should send an InputStreamFile attachment from an update reply as multipart/form-data" in {
    val fileContent = "Lorem ipsum"
    val bot = new WebhookBot[IO](api, url = "localhost") {
      override def onMessageReply(msg: Message): IO[Option[Method[?]]] =
        IO.pure(
          Option(
            Methods
              .sendDocument(
                ChatIntId(0),
                InputStreamFile(NamedInputStream("document.txt", new ByteArrayInputStream(fileContent.getBytes)))
              )
          )
        )
    }
    val (status, contentType, body) = (bot.start(0), BlazeClientBuilder[IO].resource).tupled
      .use { case (server, client) =>
        client.run(
          Request[IO](POST, server.baseUri).withEntity(
            parse(
              """{"update_id": 1, "message": {"message_id": 0, "date": 0,
                |"chat": {"id": 0, "type": "private"}, "text": "hi"}}""".stripMargin
            ).toOption.get
          )
        ).use { response =>
          response.as[String].map(body => (response.status, response.contentType.map(_.mediaType.toString), body))
        }
      }
      .unsafeRunSync()

    status shouldBe Status.Ok
    contentType.exists(_.contains("multipart/form-data")) shouldBe true
    body should include(fileContent)
  }

}
