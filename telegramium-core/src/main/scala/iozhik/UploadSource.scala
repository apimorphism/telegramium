package iozhik

import io.circe.Decoder
import io.circe.Encoder
import io.circe.syntax._

/** The content of a file to upload. Backends provide implementations; the JSON payload only carries the filename. */
trait UploadSource {
  def filename: String
}

object UploadSource {
  implicit val encoder: Encoder[UploadSource] = (x: UploadSource) => x.filename.asJson

  implicit val decoder: Decoder[UploadSource] =
    Decoder.failedWithMessage[UploadSource]("UploadSource cannot be decoded")

}
