package telegramium.bots.high

import cats.effect.IO
import cats.effect.Ref
import cats.effect.unsafe.implicits.global

import io.circe.Json
import org.http4s.HttpApp
import org.http4s.Response
import org.http4s.circe.*
import org.http4s.client.Client

import telegramium.bots.client.Method

/** The wire checks on every Method sent through BotApi, on a client that records the wire request */
class BotApiWireSpec extends WireSpec {

  import WireSpec.*

  protected def send(method: Method[?]): Wire =
    (for {
      recorded <- Ref.of[IO, Option[Wire]](None)
      client = Client.fromHttpApp(HttpApp[IO] { req =>
        readWire(req).flatMap(w => recorded.set(Some(w))).as(Response[IO]().withEntity(Json.obj("ok" -> Json.False)))
      })
      // the call itself fails on the response, which doesn't matter here
      result <- BotApi(client, "https://api.telegram.test").execute(method).attempt
      wire   <- recorded.get
    } yield wire.getOrElse(fail(s"${method.payload.name} sent nothing: $result"))).unsafeRunSync()

}
