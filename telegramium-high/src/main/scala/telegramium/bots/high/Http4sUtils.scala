package telegramium.bots.high

import cats.effect.{Async, Sync}
import cats.syntax.flatMap.*
import fs2.io.file.Path
import io.circe.Json
import org.http4s.multipart.{Multipart, Multiparts, Part}

import java.io.File

private[high] object Http4sUtils {

  def toFileDataParts[F[_]: Async](files: Map[String, File]): Vector[Part[F]] =
    files.map { case (filename, file) =>
      Part.fileData[F](filename, Path.fromNioPath(file.toPath))
    }.toVector

  def toMultipartWithFormData[F[_]: Sync](
    json: Json,
    fileFieldNames: List[String],
    attachments: Vector[Part[F]]
  ): F[Multipart[F]] =
    Multiparts.forSync[F].flatMap {
      _.multipart(
        json.asObject
          .map {
            _.toIterable
              .filterNot { case (n, v) =>
                fileFieldNames.contains(n) || v.isNull || v.isObject
              }
              .map { case (n, v) =>
                Part.formData[F](n, v.asString.getOrElse(v.toString))
              }
              .toVector
          }
          .getOrElse(Vector.empty) ++ attachments
      )
    }

}
