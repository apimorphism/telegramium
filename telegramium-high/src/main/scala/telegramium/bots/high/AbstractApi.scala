package telegramium.bots.high

import cats.MonadError
import cats.effect.{ConcurrentEffect, ContextShift}
import cats.syntax.flatMap._
import cats.syntax.functor._
import io.circe.syntax._
import io.circe.{Decoder, Encoder, Json}
import org.http4s._
import org.http4s.circe._
import org.http4s.client._
import org.http4s.dsl.io._
import telegramium.bots.CirceImplicits._
import telegramium.bots.{ChatId, KeyboardMarkup, Message, ParseMode}

trait BotApiMethod[Res] {
  def name: String
}

case class SendMessage(
  chatId: ChatId,
  text: String,
  parseMode: Option[ParseMode] = Option.empty,
  disableWebPagePreview: Option[Boolean] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  replyToMessageId: Option[Int] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
) extends BotApiMethod[Message] {

  override val name: String = "sendMessage"

}

object SendMessage {
  implicit lazy val sendmessageEncoder: Encoder[SendMessage] =
    (x: SendMessage) => {
      Json.fromFields(
        List(
          "method" -> x.name.asJson,
          "chat_id" -> x.chatId.asJson,
          "text" -> x.text.asJson,
          "parse_mode" -> x.parseMode.asJson,
          "disable_web_page_preview" -> x.disableWebPagePreview.asJson,
          "disable_notification" -> x.disableNotification.asJson,
          "reply_to_message_id" -> x.replyToMessageId.asJson,
          "reply_markup" -> x.replyMarkup.asJson
        ).filter(!_._2.isNull)
      )
    }
}

class AbstractApi[F[_]: ConcurrentEffect: ContextShift](
  http: Client[F],
  baseUrl: String
)(implicit F: MonadError[F, Throwable]) {

  def execute[Res, M](method: M)(implicit ev: M <:< BotApiMethod[Res], encoder: Encoder[M], decoder: Decoder[Res]): F[Res] =
    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/${method.name}"))
      req = Request[F]()
        .withMethod(POST)
        .withUri(uri)
        .withEntity(method.asJson)
      res <- decodeResponse[Res](req)
    } yield res

  // ==== utils ====

  case class Response[A: Decoder](
    ok: Boolean,
    result: Option[A],
    description: Option[String]
  )

  private implicit def responseDecoder[A: Decoder]: Decoder[Response[A]] =
    Decoder.instance { h =>
      for {
        _ok <- h.get[Boolean]("ok")
        _result <- h.get[Option[A]]("result")
        _description <- h.get[Option[String]]("description")
      } yield {
        Response[A](ok = _ok, result = _result, description = _description)
      }
    }

  private def decodeResponse[A: io.circe.Decoder](req: Request[F]): F[A] = {
    for {
      response <- http.expect(req)(jsonOf[F, Response[A]])
      result <- F.fromOption[A](
        response.result,
        new RuntimeException(response.description.getOrElse("Unknown error occurred"))
      )
    } yield {
      result
    }
  }

}

