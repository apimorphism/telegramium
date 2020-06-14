package telegramium.bots.high

import cats.MonadError
import cats.effect.{Blocker, ContextShift, Sync}
import cats.instances.map._
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.functorFilter._
import io.circe.syntax._
import io.circe.{Decoder, Encoder, Json}
import org.http4s._
import org.http4s.circe._
import org.http4s.client._
import org.http4s.dsl.io._
import org.http4s.multipart.{Multipart, Part}
import telegramium.bots.CirceImplicits._
import telegramium.bots.{ChatId, IFile, InputPartFile, KeyboardMarkup, MaskPosition, Message, ParseMode}

sealed trait Method[Res] {
  def name: String
}

object Method {
  implicit def methodOps[Res](method: Method[Res]): MethodSyntax[Res] = new MethodSyntax[Res](method)

  final class MethodSyntax[Res](private val method: Method[Res]) extends AnyVal {
    def exec[F[_]](implicit api: BotApi[F]): F[Res] = api.exec(method)
  }
}

final case class MethodReq[Res](
  name: String,
  decoder: Decoder[Res],
  json: Json,
  files: Map[String, IFile]
) extends Method[Res]

object MethodReq {
  def apply[Res](
    name: String,
    json: Json,
    files: Map[String, IFile] = Map.empty
  )(implicit decoder: Decoder[Res]): MethodReq[Res] =
    new MethodReq(name, decoder, json, files)
}

final case class SendMessageReq(
  chatId: ChatId,
  text: String,
  parseMode: Option[ParseMode] = Option.empty,
  disableWebPagePreview: Option[Boolean] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  replyToMessageId: Option[Int] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)

object SendMessageReq {
  implicit lazy val sendmessagereqEncoder: Encoder[SendMessageReq] =
    (x: SendMessageReq) => {
      Json.fromFields(
        List(
          "method" -> "sendMessage".asJson,
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

final case class AddStickerToSetReq(
  userId: Int,
  name: String,
  pngSticker: Option[IFile] = Option.empty,
  tgsSticker: Option[IFile] = Option.empty,
  emojis: String,
  maskPosition: Option[MaskPosition] = Option.empty
)

object AddStickerToSetReq {
  implicit lazy val addstickertosetreqEncoder: Encoder[AddStickerToSetReq] =
    (x: AddStickerToSetReq) => {
      Json.fromFields(
        List(
          "user_id" -> x.userId.asJson,
          "name" -> x.name.asJson,
          "png_sticker" -> x.pngSticker.asJson,
          "tgs_sticker" -> x.tgsSticker.asJson,
          "emojis" -> x.emojis.asJson,
          "mask_position" -> x.maskPosition.asJson,
          "method" -> "addStickerToSet".asJson
        ).filter(!_._2.isNull)
      )
    }
}

final case class SendDocumentReq(
  chatId: ChatId,
  document: IFile,
  thumb: Option[IFile] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  replyToMessageId: Option[Int] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)

object SendDocumentReq {
  implicit lazy val senddocumentreqEncoder: Encoder[SendDocumentReq] =
    (x: SendDocumentReq) => {
      Json.fromFields(
        List(
          "chat_id"              -> x.chatId.asJson,
          "document"             -> Json.Null,
          "thumb"                -> x.thumb.asJson,
          "caption"              -> x.caption.asJson,
          "parse_mode"           -> x.parseMode.asJson,
          "disable_notification" -> x.disableNotification.asJson,
          "reply_to_message_id"  -> x.replyToMessageId.asJson,
          "reply_markup"         -> x.replyMarkup.asJson,
          "method" -> "sendDocument".asJson
        ).filter(!_._2.isNull)
      )
    }

}

trait Methods {
  def sendMessage(
    chatId: ChatId,
    text: String,
    parseMode: Option[ParseMode] = Option.empty,
    disableWebPagePreview: Option[Boolean] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    replyMarkup: Option[KeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendMessageReq(chatId, text, parseMode, disableWebPagePreview, disableNotification, replyToMessageId, replyMarkup)
    MethodReq("sendMessage", req.asJson)
  }

  def addStickerToSet(
    userId: Int,
    name: String,
    pngSticker: Option[IFile] = Option.empty,
    tgsSticker: Option[IFile] = Option.empty,
    emojis: String,
    maskPosition: Option[MaskPosition] = Option.empty
  ): Method[Boolean] = {
    val req = AddStickerToSetReq(userId, name, pngSticker, tgsSticker, emojis, maskPosition)
    MethodReq("addStickerToSet", req.asJson, Map("png_sticker" -> pngSticker, "tgs_sticker" -> tgsSticker).mapFilter(identity))
  }

  def sendDocument(
    chatId: ChatId,
    document: IFile,
    thumb: Option[IFile] = Option.empty,
    caption: Option[String] = Option.empty,
    parseMode: Option[ParseMode] = Option.empty,
    disableNotification: Option[Boolean] = Option.empty,
    replyToMessageId: Option[Int] = Option.empty,
    replyMarkup: Option[KeyboardMarkup] = Option.empty
  ): Method[Message] = {
    val req = SendDocumentReq(chatId, document, thumb, caption, parseMode, disableNotification, replyToMessageId, replyMarkup)
    MethodReq("sendDocument", req.asJson, Map("document" -> Option(document), "thumb" -> thumb).mapFilter(identity))
  }
}

object Methods extends Methods

class BotApi[F[_]: Sync: ContextShift](
  http: Client[F],
  baseUrl: String,
  blocker: Blocker
)(implicit F: MonadError[F, Throwable]) {
  def exec[Res](method: Method[Res]): F[Res] = {
    val methodReq = method.asInstanceOf[MethodReq[Res]]
    val attachments = methodReq.files.collect {
      case (filename, InputPartFile(file)) => Part.fileData[F](filename, file, blocker)
    }
      .toVector

    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/${method.name}"))
      req = mkRequest(uri, methodReq.json, attachments)
      res <- decodeResponse[Res](req)(methodReq.decoder)
    } yield res
  }

  private def mkRequest(uri: Uri, json: Json, attachments: Vector[Part[F]]) = {
    val reqBuilder = Request[F]().withMethod(POST).withUri(uri)
    if (attachments.isEmpty)
      reqBuilder.withEntity(json)
    else {
      val parts = Multipart[F] {
        json.asObject.map(
          _.toIterable.filterNot(_._2.isNull).map { case (n, v) => Part.formData[F](n, v.asString.getOrElse(v.toString)) }.toVector
        ).getOrElse(Vector.empty) ++ attachments
      }
      reqBuilder.withEntity(parts).withHeaders(parts.headers)
    }
  }

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
