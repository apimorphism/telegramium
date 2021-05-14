package telegramium.bots.high

import cats.MonadError
import cats.effect.{ConcurrentEffect, Sync}
import cats.syntax.applicativeError._
import cats.syntax.apply._
import cats.syntax.flatMap._
import cats.syntax.functor._
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger
import io.circe.{Decoder, Json}
import org.http4s.circe._
import org.http4s.client._
import org.http4s.dsl.io._
import org.http4s.multipart.Part
import org.http4s.{Request, Uri}
import telegramium.bots.InputPartFile
import telegramium.bots.client.Method
import telegramium.bots.high.Http4sUtils.{toFileDataParts, toMultipartWithFormData}

class BotApi[F[_]: Sync: ContextShift: Logger](
  http: Client[F],
  baseUrl: String,
  blocker: Blocker
)(implicit F: MonadError[F, Throwable]) extends Api[F] {
  override def execute[Res](method: Method[Res]): F[Res] = {
    val inputPartFiles = method.payload.files.collect {
      case (filename, InputPartFile(file)) => (filename, file)
    }
    val attachments = toFileDataParts(inputPartFiles, blocker)

    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/${method.payload.name}"))
      req = mkRequest(uri, method.payload.json, inputPartFiles.keys.toList, attachments)
      res <- handleResponse[Res](method, req)(method.decoder)
    } yield res
  }

  private def mkRequest(uri: Uri, json: Json, fileFieldNames: List[String], attachments: Vector[Part[F]]) = {
    val reqBuilder = Request[F]().withMethod(POST).withUri(uri)
    if (attachments.isEmpty)
      reqBuilder.withEntity(json)
    else {
      val parts = toMultipartWithFormData(json, fileFieldNames, attachments)
      reqBuilder.withEntity(parts).withHeaders(parts.headers)
    }
  }

  private case class Response[A](
    ok: Boolean,
    result: Option[A],
    description: Option[String],
    errorCode: Option[Int]
  )

  private implicit def responseDecoder[A: Decoder]: Decoder[Response[A]] =
    Decoder.instance { h =>
      for {
        _ok <- h.get[Boolean]("ok")
        _result <- h.get[Option[A]]("result")
        _description <- h.get[Option[String]]("description")
        _errorCode <- h.get[Option[Int]]("error_code")
      } yield {
        Response[A](ok = _ok, result = _result, description = _description, errorCode = _errorCode)
      }
    }

  private def handleResponse[A: io.circe.Decoder](method: Method[A], req: Request[F]): F[A] =
    for {
      response <- http.fetchAs(req)(jsonOf[F, Response[A]])
      result <- response match {
        case Response(true, Some(result), _, _) => F.pure(result)
        case Response(_, _, description, errorCode) =>
          val code = errorCode.map(_.toString).getOrElse("")
          val desc = description.getOrElse("")
          val methodName = method.payload.name
          val json = method.payload.json
          Logger[F].error(
            s"""Telegram Bot API request failed: code=$code description="$desc" method=$methodName, JSON: \n$json"""
          ) *> FailedRequest(method, errorCode, description).raiseError[F, A]
      }
    } yield result

}

object BotApi {
  /**
   * @param blocker The `Blocker` to use for blocking IO operations. If not provided, a default `Blocker` will be used.
   */
  def apply[F[_]: ConcurrentEffect: ContextShift](http: Client[F], baseUrl: String): BotApi[F] =
    new BotApi[F](http, baseUrl, blocker)

  private implicit def defaultLogger[F[_]: Sync]: Logger[F] = Slf4jLogger.getLogger[F]
}
