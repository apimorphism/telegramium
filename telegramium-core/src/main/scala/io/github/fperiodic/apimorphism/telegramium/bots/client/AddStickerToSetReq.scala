package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.IFile
import io.github.fperiodic.apimorphism.telegramium.bots.MaskPosition

final case class AddStickerToSetReq(
                                    /** User identifier of sticker set owner*/
                                    userId: Int,
                                    /** Sticker set name*/
                                    name: String,
                                    /** Png image with the sticker, must be up to 512 kilobytes in
                                      * size, dimensions must not exceed 512px, and either width or
                                      * height must be exactly 512px. Pass a file_id as a String to
                                      * send a file that already exists on the Telegram servers,
                                      * pass an HTTP URL as a String for Telegram to get a file from
                                      * the Internet, or upload a new one using multipart/form-data.
                                      * More info on Sending Files »*/
                                    pngSticker: IFile,
                                    /** One or more emoji corresponding to the sticker*/
                                    emojis: String,
                                    /** A JSON-serialized object for position where the mask should
                                      * be placed on faces*/
                                    maskPosition: Option[MaskPosition] = Option.empty)
