package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.IFile
import telegramium.bots.ParseMode
import telegramium.bots.KeyboardMarkup

final case class SendAnimationReq(
                                  /** Unique identifier for the target chat or username of the
                                    * target channel (in the format @channelusername)*/
                                  chatId: ChatId,
                                  /** Animation to send. Pass a file_id as String to send an
                                    * animation that exists on the Telegram servers (recommended),
                                    * pass an HTTP URL as a String for Telegram to get an
                                    * animation from the Internet, or upload a new animation using
                                    * multipart/form-data. More info on Sending Files »*/
                                  animation: IFile,
                                  /** Duration of sent animation in seconds*/
                                  duration: Option[Int] = Option.empty,
                                  /** Animation width*/
                                  width: Option[Int] = Option.empty,
                                  /** Animation height*/
                                  height: Option[Int] = Option.empty,
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
                                  /** Animation caption (may also be used when resending
                                    * animation by file_id), 0-1024 characters after entities
                                    * parsing*/
                                  caption: Option[String] = Option.empty,
                                  /** Mode for parsing entities in the animation caption. See
                                    * formatting options for more details.*/
                                  parseMode: Option[ParseMode] = Option.empty,
                                  /** Sends the message silently. Users will receive a
                                    * notification with no sound.*/
                                  disableNotification: Option[Boolean] = Option.empty,
                                  /** If the message is a reply, ID of the original message*/
                                  replyToMessageId: Option[Int] = Option.empty,
                                  /** Additional interface options. A JSON-serialized object for
                                    * an inline keyboard, custom reply keyboard, instructions to
                                    * remove reply keyboard or to force a reply from the user.*/
                                  replyMarkup: Option[KeyboardMarkup] = Option.empty)
