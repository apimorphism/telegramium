package telegramium.bots.client

import telegramium.bots.IFile

/** @param name
  *   Sticker set name
  * @param userId
  *   User identifier of the sticker set owner
  * @param thumb
  *   A PNG image with the thumbnail, must be up to 128 kilobytes in size and have width and height exactly 100px, or a
  *   TGS animation with the thumbnail up to 32 kilobytes in size; see
  *   https://core.telegram.org/stickers#animated-sticker-requirements for animated sticker technical requirements, or a
  *   WEBM video with the thumbnail up to 32 kilobytes in size; see
  *   https://core.telegram.org/stickers#video-sticker-requirements for video sticker technical requirements. Pass a
  *   file_id as a String to send a file that already exists on the Telegram servers, pass an HTTP URL as a String for
  *   Telegram to get a file from the Internet, or upload a new one using multipart/form-data. Animated sticker set
  *   thumbnails can't be uploaded via HTTP URL.
  */
final case class SetStickerSetThumbReq(name: String, userId: Long, thumb: Option[IFile] = Option.empty)
