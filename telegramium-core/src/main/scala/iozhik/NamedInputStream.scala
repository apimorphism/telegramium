package iozhik

import io.circe.Decoder
import io.circe.Encoder
import io.circe.syntax._

/** A file to upload from an input stream, opened lazily and read exactly once per request attempt, without loading the
  * whole file into memory.
  */
final case class NamedInputStream(filename: String, openStream: () => java.io.InputStream)

object NamedInputStream {
  implicit val encoder: Encoder[NamedInputStream] = (x: NamedInputStream) => x.filename.asJson

  implicit val decoder: Decoder[NamedInputStream] =
    Decoder.failedWithMessage[NamedInputStream]("NamedInputStream cannot be decoded")

}
