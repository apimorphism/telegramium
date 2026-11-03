package telegramium.bots.client

import io.circe.{Decoder, Json}
import telegramium.bots.IFile

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

}
