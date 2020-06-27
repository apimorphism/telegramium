package telegramium.bots.high

import cats.MonadError
import cats.effect.{Blocker, ConcurrentEffect, ContextShift, Sync}
import cats.syntax.flatMap._
import cats.syntax.functor._
import io.circe.{Decoder, Json}
import org.http4s.circe._
import org.http4s.client._
import org.http4s.dsl.io._
import org.http4s.multipart.Part
import org.http4s.{Request, Uri}
import telegramium.bots.InputPartFile
import telegramium.bots.client.{Method, MethodReq}
import telegramium.bots.high.Http4sUtils.{toFileDataParts, toMultipartWithFormData}

class BotApi[F[_]: Sync: ContextShift](
  http: Client[F],
  baseUrl: String,
  blocker: Blocker
)(implicit F: MonadError[F, Throwable]) extends Api[F] {
  override def execute[Res](method: Method[Res]): F[Res] = {
    val methodReq = method.asInstanceOf[MethodReq[Res]]
    val inputPartFiles = methodReq.files.collect {
      case (filename, InputPartFile(file)) => (filename, file)
    }
    val attachments = toFileDataParts(inputPartFiles, blocker)

    for {
      uri <- F.fromEither[Uri](Uri.fromString(s"$baseUrl/${method.name}"))
      req = mkRequest(uri, methodReq.json, inputPartFiles.keys.toList, attachments)
      res <- decodeResponse[Res](req)(methodReq.decoder)
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

  private case class Response[A: Decoder](
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

  private def decodeResponse[A: io.circe.Decoder](req: Request[F]): F[A] =
    for {
      response <- http.expect(req)(jsonOf[F, Response[A]])
      result <- F.fromOption[A](
        response.result,
        new RuntimeException(response.description.getOrElse("Unknown error occurred"))
      )
    } yield result

}

object BotApi {
  /**
   * @param blocker The `Blocker` to use for blocking IO operations. If not provided, a default `Blocker` will be used.
   */
  def apply[F[_]: ConcurrentEffect: ContextShift](http: Client[F], baseUrl: String, blocker: Blocker = DefaultBlocker.blocker): BotApi[F] =
    new BotApi[F](http, baseUrl, blocker)
}
