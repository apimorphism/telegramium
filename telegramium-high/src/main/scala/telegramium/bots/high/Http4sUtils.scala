package telegramium.bots.high

import java.io.File

import cats.effect.Async
import cats.effect.LiftIO
import cats.effect.Sync
import cats.syntax.all.*

import fs2.io.file.Files.forAsync
import fs2.io.file.Path
import io.circe.Json
import iozhik.UploadSource
import org.http4s.multipart.Multipart
import org.http4s.multipart.Multiparts
import org.http4s.multipart.Part

private[high] object Http4sUtils {

  def toFileDataParts[F[_]: Async](files: Map[String, File]): Vector[Part[F]] =
    files.map { case (filename, file) =>
      Part.fileData[F](filename, Path.fromNioPath(file.toPath))(using forAsync)
    }.toVector

  def toStreamDataParts[F[_]: Sync: LiftIO](files: Map[String, UploadSource]): F[Vector[Part[F]]] =
    files.toVector.traverse {
      case (fieldName, StreamUpload(filename, stream)) =>
        Part.fileData[F](fieldName, filename, stream.translate(LiftIO.liftK[F])).pure[F]
      case (_, other) => Sync[F].raiseError[Part[F]](new UnsupportedUploadSource(other))
    }

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
