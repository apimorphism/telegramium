package telegramium.bots.client

import telegramium.bots.IFile
import telegramium.bots.MaskPosition

/** @param userId
  *   User identifier of sticker set owner
  * @param name
  *   Sticker set name
  * @param pngSticker
  *   PNG image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px, and either
  *   width or height must be exactly 512px. Pass a file_id as a String to send a file that already exists on the
  *   Telegram servers, pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one
  *   using multipart/form-data.
  * @param tgsSticker
  *   TGS animation with the sticker, uploaded using multipart/form-data. See
  *   https://core.telegram.org/animated_stickers#technical-requirements for technical requirements
  * @param emojis
  *   One or more emoji corresponding to the sticker
  * @param maskPosition
  *   A JSON-serialized object for position where the mask should be placed on faces
  */
final case class AddStickerToSetReq(
  userId: Long,
  name: String,
  pngSticker: Option[IFile] = Option.empty,
  tgsSticker: Option[IFile] = Option.empty,
  emojis: String,
  maskPosition: Option[MaskPosition] = Option.empty
)
