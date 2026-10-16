package iozhik

import io.circe.Decoder
import io.circe.Encoder
import io.circe.syntax._

/** A file to upload from an input stream, without loading the whole file into memory. `openStream` is called each time
  * the upload is read, so every read gets a fresh stream.
  */
final case class NamedInputStream(filename: String, openStream: () => java.io.InputStream)

object NamedInputStream {
  implicit val encoder: Encoder[NamedInputStream] = (x: NamedInputStream) => x.filename.asJson

  implicit val decoder: Decoder[NamedInputStream] =
    Decoder.failedWithMessage[NamedInputStream]("NamedInputStream cannot be decoded")

}
