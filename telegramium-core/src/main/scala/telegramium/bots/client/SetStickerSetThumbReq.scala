package telegramium.bots.client

import telegramium.bots.IFile

final case class SetStickerSetThumbReq(
                                       /** Sticker set name*/
                                       name: String,
                                       /** User identifier of the sticker set owner*/
                                       userId: Int,
                                       /** A PNG image with the thumbnail, must be up to 128 kilobytes
                                         * in size and have width and height exactly 100px, or a TGS
                                         * animation with the thumbnail up to 32 kilobytes in size; see
                                         * https://core.telegram.org/animated_stickers#technical-requirements
                                         * for animated sticker technical requirements. Pass a file_id
                                         * as a String to send a file that already exists on the
                                         * Telegram servers, pass an HTTP URL as a String for Telegram
                                         * to get a file from the Internet, or upload a new one using
                                         * multipart/form-data. More info on Sending Files ». Animated
                                         * sticker set thumbnail can't be uploaded via HTTP URL.*/
                                       thumb: Option[IFile] = Option.empty)
