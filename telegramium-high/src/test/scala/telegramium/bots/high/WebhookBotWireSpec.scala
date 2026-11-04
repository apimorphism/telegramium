package telegramium.bots.high

import cats.effect.IO
import cats.effect.unsafe.implicits.global

import io.circe.parser.parse
import org.http4s.Method.POST
import org.http4s.Request
import org.http4s.Status
import org.http4s.circe.*
import org.http4s.implicits.*

import telegramium.bots.Message
import telegramium.bots.client.Method

/** The wire checks on every Method returned as a webhook reply, read from the response of WebhookBot's routes */
class WebhookBotWireSpec extends WireSpec {

  import WireSpec.*

  // the bot never calls the API here: routes() doesn't set the webhook
  private val api: Api[IO] = new Api[IO] {
    override def execute[Res](method: Method[Res]): IO[Res] = IO.raiseError(new UnsupportedOperationException)
  }

  private val update =
    parse(
      """{"update_id": 1, "message": {"message_id": 0, "date": 0, "chat": {"id": 0, "type": "private"}}}"""
    ).toOption.get

  protected def send(method: Method[?]): Wire = {
    val bot = new WebhookBot[IO](api, url = "localhost") {
      override def onMessageReply(msg: Message): IO[Option[Method[?]]] = IO.pure(Some(method))
    }
    bot
      .routes()
      .orNotFound
      .run(Request[IO](POST, uri"/").withEntity(update))
      .flatMap { response =>
        if (response.status == Status.Ok) readWire(response)
        else IO(fail(s"${method.payload.name} replied ${response.status}"))
      }
      .unsafeRunSync()
  }

}
