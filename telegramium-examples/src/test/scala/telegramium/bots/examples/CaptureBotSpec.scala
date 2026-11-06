package telegramium.bots.examples

import java.nio.file.Files
import scala.concurrent.duration.*

import cats.effect.Deferred
import cats.effect.IO
import cats.effect.unsafe.implicits.global

import io.circe.Json
import org.http4s.HttpApp
import org.http4s.Response
import org.http4s.Status
import org.http4s.circe.*
import org.http4s.client.Client
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class CaptureBotSpec extends AnyFreeSpec with Matchers {

  private val token = "123456:SECRET"

  private def capture(app: HttpApp[IO]) =
    new CaptureBot.Capture(Client.fromHttpApp(app), token, Files.createTempDirectory("capture"))

  "a call Telegram refuses fails with Telegram's description, not the request URI and its token" in {
    val refusing = capture(HttpApp[IO] { _ =>
      IO.pure(
        Response[IO](Status.BadRequest).withEntity(
          Json.obj(
            "ok"          -> Json.False,
            "error_code"  -> Json.fromInt(400),
            "description" -> Json.fromString("Bad Request: chat not found")
          )
        )
      )
    })
    val error = refusing.call("getChat", "chat_id" -> Json.fromLong(1)).attempt.unsafeRunSync().left.toOption.get
    error.getMessage shouldBe "getChat failed: Bad Request: chat not found"
  }

  "an update it fails to handle is passed over, not fetched again" in {
    // a chat without an ID, which recording the chat fails on
    val update = Json.obj(
      "update_id" -> Json.fromLong(1),
      "message"   -> Json.obj("message_id" -> Json.fromLong(1), "chat" -> Json.obj("type" -> Json.fromString("group")))
    )
    def ok(result: Json) = Response[IO]().withEntity(Json.obj("ok" -> Json.True, "result" -> result))
    val passedOver       = for {
      next <- Deferred[IO, Long]
      app = HttpApp[IO] { req =>
        req.uri.path.segments.last.encoded match {
          case "getUpdates" =>
            req.as[Json].map(_.hcursor.get[Long]("offset").getOrElse(0L)).flatMap {
              case 0L     => IO.pure(ok(Json.arr(update)))
              case offset => next.complete(offset).as(ok(Json.arr()))
            }
          case _ => IO.pure(ok(Json.obj()))
        }
      }
      offset <- IO.race(capture(app).run, next.get).timeout(10.seconds)
    } yield offset
    passedOver.unsafeRunSync() shouldBe Right(2L)
  }

}
