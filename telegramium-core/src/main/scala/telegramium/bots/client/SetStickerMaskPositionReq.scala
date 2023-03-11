package telegramium.bots.client

import telegramium.bots.MaskPosition

/** @param sticker
  *   File identifier of the sticker
  * @param maskPosition
  *   A JSON-serialized object with the position where the mask should be placed on faces. Omit the parameter to remove
  *   the mask position.
  */
final case class SetStickerMaskPositionReq(sticker: String, maskPosition: Option[MaskPosition] = Option.empty)
