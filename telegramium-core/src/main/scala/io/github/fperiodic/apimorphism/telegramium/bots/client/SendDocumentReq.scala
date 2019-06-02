package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.ChatId
import io.github.fperiodic.apimorphism.telegramium.bots.IFile
import io.github.fperiodic.apimorphism.telegramium.bots.KeyboardMarkup

final case class SendDocumentReq(
                                 /** Unique identifier for the target chat or username of the
                                   * target channel (in the format @channelusername)*/
                                 chatId: ChatId,
                                 /** File to send. Pass a file_id as String to send a file that
                                   * exists on the Telegram servers (recommended), pass an HTTP
                                   * URL as a String for Telegram to get a file from the
                                   * Internet, or upload a new one using multipart/form-data.
                                   * More info on Sending Files »*/
                                 document: IFile,
                                 /** Thumbnail of the file sent; can be ignored if thumbnail
                                   * generation for the file is supported server-side. The
                                   * thumbnail should be in JPEG format and less than 200 kB in
                                   * size. A thumbnail‘s width and height should not exceed 320.
                                   * Ignored if the file is not uploaded using
                                   * multipart/form-data. Thumbnails can’t be reused and can be
                                   * only uploaded as a new file, so you can pass
                                   * “attach://<file_attach_name>” if the thumbnail was uploaded
                                   * using multipart/form-data under <file_attach_name>. More
                                   * info on Sending Files »*/
                                 thumb: Option[IFile] = Option.empty,
                                 /** Document caption (may also be used when resending documents
                                   * by file_id), 0-1024 characters*/
                                 caption: Option[String] = Option.empty,
                                 /** Send Markdown or HTML, if you want Telegram apps to show
                                   * bold, italic, fixed-width text or inline URLs in the media
                                   * caption.*/
                                 parseMode: Option[String] = Option.empty,
                                 /** Sends the message silently. Users will receive a
                                   * notification with no sound.*/
                                 disableNotification: Option[Boolean] = Option.empty,
                                 /** If the message is a reply, ID of the original message*/
                                 replyToMessageId: Option[Int] = Option.empty,
                                 /** Additional interface options. A JSON-serialized object for
                                   * an inline keyboard, custom reply keyboard, instructions to
                                   * remove reply keyboard or to force a reply from the user.*/
                                 replyMarkup: Option[KeyboardMarkup] = Option.empty)
