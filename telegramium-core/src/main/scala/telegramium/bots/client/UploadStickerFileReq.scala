package telegramium.bots.client

import telegramium.bots.IFile

/** @param userId
  *   User identifier of sticker file owner
  * @param sticker
  *   A file with the sticker in .WEBP, .PNG, .TGS, or .WEBM format. See https://core.telegram.org/stickers for
  *   technical requirements.
  * @param stickerFormat
  *   Format of the sticker, must be one of “static”, “animated”, “video”
  */
final case class UploadStickerFileReq(userId: Long, sticker: IFile, stickerFormat: String)
