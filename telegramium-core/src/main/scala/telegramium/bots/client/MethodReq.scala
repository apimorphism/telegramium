package telegramium.bots.client

import io.circe.{Decoder, Json}
import telegramium.bots.IFile

case class MethodReq[Res](
    name: String,
    decoder: Decoder[Res],
    json: Json,
    files: Map[String, IFile]
) extends Method[Res]

object MethodReq {

  def apply[Res](
      name: String,
      json: Json,
      files: Map[String, IFile] = Map.empty
  )(implicit decoder: Decoder[Res]): MethodReq[Res] =
    new MethodReq(name, decoder, json, files)

}
