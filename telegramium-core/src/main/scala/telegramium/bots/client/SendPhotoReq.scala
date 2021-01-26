package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.IFile
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.KeyboardMarkup

final case class SendPhotoReq(
                              /** Unique identifier for the target chat or username of the
                                * target channel (in the format &#064;channelusername)*/
                              chatId: ChatId,
                              /** Photo to send. Pass a file_id as String to send a photo
                                * that exists on the Telegram servers (recommended), pass an
                                * HTTP URL as a String for Telegram to get a photo from the
                                * Internet, or upload a new photo using multipart/form-data.
                                * The photo must be at most 10 MB in size. The photo's width
                                * and height must not exceed 10000 in total. Width and height
                                * ratio must be at most 20.*/
                              photo: IFile,
                              /** Photo caption (may also be used when resending photos by
                                * file_id), 0-1024 characters after entities parsing*/
                              caption: Option[String] = Option.empty,
                              /** Mode for parsing entities in the photo caption. See
                                * formatting options for more details.*/
                              parseMode: Option[ParseMode] = Option.empty,
                              /** List of special entities that appear in the caption, which
                                * can be specified instead of parse_mode*/
                              captionEntities: List[MessageEntity] = List.empty,
                              /** Sends the message silently. Users will receive a
                                * notification with no sound.*/
                              disableNotification: Option[Boolean] = Option.empty,
                              /** If the message is a reply, ID of the original message*/
                              replyToMessageId: Option[Int] = Option.empty,
                              /** Pass True, if the message should be sent even if the
                                * specified replied-to message is not found*/
                              allowSendingWithoutReply: Option[Boolean] = Option.empty,
                              /** Additional interface options. A JSON-serialized object for
                                * an inline keyboard, custom reply keyboard, instructions to
                                * remove reply keyboard or to force a reply from the user.*/
                              replyMarkup: Option[KeyboardMarkup] = Option.empty)
