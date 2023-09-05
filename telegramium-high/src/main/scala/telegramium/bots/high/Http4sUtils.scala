package telegramium.bots.high

import java.io.File

import cats.effect.Async
import cats.effect.Sync
import cats.syntax.flatMap.*

import fs2.io.file.Files.forAsync
import fs2.io.file.Path
import io.circe.Json
import org.http4s.multipart.Multipart
import org.http4s.multipart.Multiparts
import org.http4s.multipart.Part

private[high] object Http4sUtils {

  def toFileDataParts[F[_]: Async](files: Map[String, File]): Vector[Part[F]] =
    files.map { case (filename, file) =>
      Part.fileData[F](filename, Path.fromNioPath(file.toPath))(forAsync)
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
