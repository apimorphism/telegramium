package telegramium.bots.client

import telegramium.bots.IFile
import telegramium.bots.MaskPosition

final case class AddStickerToSetReq(
                                    /** User identifier of sticker set owner*/
                                    userId: Int,
                                    /** Sticker set name*/
                                    name: String,
                                    /** PNG image with the sticker, must be up to 512 kilobytes in
                                      * size, dimensions must not exceed 512px, and either width or
                                      * height must be exactly 512px. Pass a file_id as a String to
                                      * send a file that already exists on the Telegram servers,
                                      * pass an HTTP URL as a String for Telegram to get a file from
                                      * the Internet, or upload a new one using multipart/form-data.
                                      * More info on Sending Files »*/
                                    pngSticker: Option[IFile] = Option.empty,
                                    /** TGS animation with the sticker, uploaded using
                                      * multipart/form-data. See
                                      * https://core.telegram.org/animated_stickers#technical-requirements
                                      * for technical requirements*/
                                    tgsSticker: Option[IFile] = Option.empty,
                                    /** One or more emoji corresponding to the sticker*/
                                    emojis: String,
                                    /** A JSON-serialized object for position where the mask should
                                      * be placed on faces*/
                                    maskPosition: Option[MaskPosition] = Option.empty)
