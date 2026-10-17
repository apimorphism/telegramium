package telegramium.bots.high

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

import cats.effect.IO
import cats.effect.Ref
import cats.effect.unsafe.implicits.global
import cats.syntax.all.*

import io.circe.Json
import io.circe.parser.parse
import iozhik.UploadSource
import org.http4s.Method.POST
import org.http4s.Request
import org.http4s.Status
import org.http4s.circe.*
import org.http4s.ember.client.EmberClientBuilder
import org.scalatest.concurrent.Eventually
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.Seconds
import org.scalatest.time.Span

import telegramium.bots.ChatIntId
import telegramium.bots.InputStreamFile
import telegramium.bots.Message
import telegramium.bots.Update
import telegramium.bots.client.Method
import telegramium.bots.client.Methods

class WebhookBotSpec extends AnyFreeSpec with Matchers with Eventually {

  // Accepts setWebhook, the only method the bot calls on start
  private val api: Api[IO] = new Api[IO] {
    override def execute[Res](method: Method[Res]): IO[Res] = IO.fromEither(method.decoder.decodeJson(Json.True))
  }

  private def post(update: String, unknownUpdates: Ref[IO, List[(Int, List[String])]]): IO[Status] = {
    val bot = new WebhookBot[IO](api, url = "localhost") {
      override def onUnknownUpdate(update: Update, unknownFields: List[String], json: Json): IO[Unit] =
        unknownUpdates.update((update.updateId, unknownFields) :: _)
    }
    (bot.start(0), EmberClientBuilder.default[IO].build).tupled.use { case (server, client) =>
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
      status <- (bot.start(0), EmberClientBuilder.default[IO].build).tupled.use { case (server, client) =>
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

  private def replyWith(reply: => Method[?]): (Status, Option[String], String) = {
    val bot = new WebhookBot[IO](api, url = "localhost") {
      override def onMessageReply(msg: Message): IO[Option[Method[?]]] = IO(Option(reply))
    }
    (bot.start(0), EmberClientBuilder.default[IO].build).tupled
      .use { case (server, client) =>
        client
          .run(
            Request[IO](POST, server.baseUri).withEntity(
              parse(
                """{"update_id": 1, "message": {"message_id": 0, "date": 0,
                  |"chat": {"id": 0, "type": "private"}, "text": "hi"}}""".stripMargin
              ).toOption.get
            )
          )
          .use { response =>
            response.as[String].map(body => (response.status, response.contentType.map(_.mediaType.toString), body))
          }
      }
      .unsafeRunSync()
  }

  "should send an InputStreamFile attachment from an update reply as multipart/form-data" in {
    val fileContent = "Lorem ipsum"
    val upload      = StreamUpload("document.txt", fs2.Stream.emits(fileContent.getBytes))

    val (status, contentType, body) = replyWith(Methods.sendDocument(ChatIntId(0), InputStreamFile(upload)))

    status shouldBe Status.Ok
    contentType.exists(_.contains("multipart/form-data")) shouldBe true
    body should include(fileContent)
    """name="document"""".r.findAllMatchIn(body).size shouldBe 1
  }

  "should finalize a streamed reply's source only after the reply body was sent" in {
    val started   = new AtomicBoolean(false)
    val finalized = new AtomicBoolean(false)
    val stream    = fs2.Stream
      .eval(IO(started.set(true)))
      .flatMap(_ => fs2.Stream.emits("STREAMED-REPLY".getBytes))
      .onFinalize(IO(finalized.set(true)))
    val upload = StreamUpload("document.txt", stream)

    // The handler returns the method before the stream has run; the body is streamed afterwards.
    val startedInHandler  = new AtomicBoolean(true)
    val (status, _, body) = replyWith {
      startedInHandler.set(started.get())
      Methods.sendDocument(ChatIntId(0), InputStreamFile(upload))
    }

    startedInHandler.get() shouldBe false
    status shouldBe Status.Ok
    body should include("STREAMED-REPLY")
    eventually(timeout(Span(5, Seconds)))(finalized.get() shouldBe true)
  }

  "should answer 200 without retrying the handler, if a reply's upload source is unsupported" in {
    val handled = new AtomicInteger(0)
    val custom  = new UploadSource { val filename = "custom.bin" }

    val (status, _, body) = replyWith {
      handled.incrementAndGet()
      Methods.sendDocument(ChatIntId(0), InputStreamFile(custom))
    }

    status shouldBe Status.Ok
    body shouldBe empty
    handled.get() shouldBe 1
  }

}
