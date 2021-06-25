package telegramium.bots.client

import telegramium.bots.IFile

/**
 * @param userId User identifier of sticker file owner
 * @param pngSticker PNG image with the sticker, must be up to 512 kilobytes in
 * size, dimensions must not exceed 512px, and either width or
 * height must be exactly 512px.
 */
final case class UploadStickerFileReq(userId: Int, pngSticker: IFile)
