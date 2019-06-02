package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.IFile

final case class UploadStickerFileReq(
                                      /** User identifier of sticker file owner*/
                                      userId: Int,
                                      /** Png image with the sticker, must be up to 512 kilobytes in
                                        * size, dimensions must not exceed 512px, and either width or
                                        * height must be exactly 512px. More info on Sending Files »*/
                                      pngSticker: IFile)
