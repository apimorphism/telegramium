package telegramium.bots.high

import java.io.File

import cats.effect.{Blocker, ContextShift, Sync}
import io.circe.Json
import org.http4s.multipart.{Multipart, Part}

private[high] object Http4sUtils {

  def toFileDataParts[F[_]: Sync: ContextShift](files: Map[String, File], blocker: Blocker): Vector[Part[F]] =
    files.map { case (filename, file) =>
      Part.fileData[F](filename, file, blocker)
    }.toVector

  def toMultipartWithFormData[F[_]](
    json: Json,
    fileFieldNames: List[String],
    attachments: Vector[Part[F]]
  ): Multipart[F] =
    Multipart[F] {
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
    }

}
