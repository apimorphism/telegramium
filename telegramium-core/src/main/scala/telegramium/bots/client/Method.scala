package telegramium.bots.client

import io.circe.{Decoder, Json}
import telegramium.bots.IFile
import telegramium.bots.InputLinkFile

trait Method[Res] {
  def payload: MethodPayload
  def decoder: Decoder[Res]
}

final case class MethodReq[Res](
  payload: MethodPayload,
  decoder: Decoder[Res]
) extends Method[Res]

final case class MethodPayload(
  name: String,
  json: Json,
  files: Map[String, IFile]
)

object MethodReq {

  def apply[Res](
    name: String,
    json: Json,
    files: Map[String, IFile] = Map.empty
  )(implicit decoder: Decoder[Res]): MethodReq[Res] =
    new MethodReq(MethodPayload(name, json, files), decoder)

  // A file embedded one level inside a compound parameter (e.g. InputMedia) can't be attached by name
  // directly, since Telegram only recognizes "attach://<name>" references nested inside the JSON body.
  // These rewrite such a field to that placeholder and collect the real IFile for multipart upload,
  // leaving file_id/URL/already-"attach://" references (InputLinkFile) untouched.
  def attachFile(name: String, file: IFile): (IFile, Map[String, IFile]) = file match {
    case _: InputLinkFile => (file, Map.empty)
    case other            => (InputLinkFile("attach://" + name), Map(name -> other))
  }

  def attachFile(name: String, file: Option[IFile]): (Option[IFile], Map[String, IFile]) = file match {
    case Some(f) =>
      val (nf, files) = attachFile(name, f)
      (Some(nf), files)
    case None => (None, Map.empty)
  }

}
