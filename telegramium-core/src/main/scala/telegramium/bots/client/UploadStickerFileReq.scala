package telegramium.bots.client

import telegramium.bots.IFile

final case class UploadStickerFileReq(
                                      /** User identifier of sticker file owner*/
                                      userId: Int,
                                      /** PNG image with the sticker, must be up to 512 kilobytes in
                                        * size, dimensions must not exceed 512px, and either width or
                                        * height must be exactly 512px.*/
                                      pngSticker: IFile)
