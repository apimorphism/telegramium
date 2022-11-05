package telegramium.bots.client

import telegramium.bots.IFile
import telegramium.bots.MaskPosition

/** @param userId
  *   User identifier of created sticker set owner
  * @param name
  *   Short name of sticker set, to be used in t.me/addstickers/ URLs (e.g., animals). Can contain only English letters,
  *   digits and underscores. Must begin with a letter, can't contain consecutive underscores and must end in
  *   "_by_<bot_username>". <bot_username> is case insensitive. 1-64 characters.
  * @param title
  *   Sticker set title, 1-64 characters
  * @param emojis
  *   One or more emoji corresponding to the sticker
  * @param pngSticker
  *   PNG image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px, and either
  *   width or height must be exactly 512px. Pass a file_id as a String to send a file that already exists on the
  *   Telegram servers, pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one
  *   using multipart/form-data.
  * @param tgsSticker
  *   TGS animation with the sticker, uploaded using multipart/form-data. See
  *   https://core.telegram.org/stickers#animated-sticker-requirements for technical requirements
  * @param webmSticker
  *   WEBM video with the sticker, uploaded using multipart/form-data. See
  *   https://core.telegram.org/stickers#video-sticker-requirements for technical requirements
  * @param stickerType
  *   Type of stickers in the set, pass “regular” or “mask”. Custom emoji sticker sets can't be created via the Bot API
  *   at the moment. By default, a regular sticker set is created.
  * @param maskPosition
  *   A JSON-serialized object for position where the mask should be placed on faces
  */
final case class CreateNewStickerSetReq(
  userId: Long,
  name: String,
  title: String,
  emojis: String,
  pngSticker: Option[IFile] = Option.empty,
  tgsSticker: Option[IFile] = Option.empty,
  webmSticker: Option[IFile] = Option.empty,
  stickerType: Option[String] = Option.empty,
  maskPosition: Option[MaskPosition] = Option.empty
)
