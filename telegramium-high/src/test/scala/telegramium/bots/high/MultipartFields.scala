package telegramium.bots.high

import cats.effect.IO
import cats.syntax.traverse.*

import org.http4s.multipart.Multipart

object MultipartFields {

  /** Form fields (parts without a filename) of a multipart body, as name -> text */
  def formFields(multipart: Multipart[IO]): IO[Map[String, String]] =
    multipart.parts
      .filter(_.filename.isEmpty)
      .traverse(part => part.bodyText.compile.string.map(part.name.getOrElse("") -> _))
      .map(_.toMap)

  /** File parts of a multipart body, as name -> filename */
  def fileParts(multipart: Multipart[IO]): Vector[(String, String)] =
    multipart.parts.flatMap(part => part.filename.map(part.name.getOrElse("") -> _))

}
