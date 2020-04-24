package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.IFile
import telegramium.bots.ParseMode
import telegramium.bots.KeyboardMarkup

final case class SendVoiceReq(
                              /** Unique identifier for the target chat or username of the
                                * target channel (in the format @channelusername)*/
                              chatId: ChatId,
                              /** Audio file to send. Pass a file_id as String to send a file
                                * that exists on the Telegram servers (recommended), pass an
                                * HTTP URL as a String for Telegram to get a file from the
                                * Internet, or upload a new one using multipart/form-data.
                                * More info on Sending Files »*/
                              voice: IFile,
                              /** Voice message caption, 0-1024 characters after entities
                                * parsing*/
                              caption: Option[String] = Option.empty,
                              /** Mode for parsing entities in the voice message caption. See
                                * formatting options for more details.*/
                              parseMode: Option[ParseMode] = Option.empty,
                              /** Duration of the voice message in seconds*/
                              duration: Option[Int] = Option.empty,
                              /** Sends the message silently. Users will receive a
                                * notification with no sound.*/
                              disableNotification: Option[Boolean] = Option.empty,
                              /** If the message is a reply, ID of the original message*/
                              replyToMessageId: Option[Int] = Option.empty,
                              /** Additional interface options. A JSON-serialized object for
                                * an inline keyboard, custom reply keyboard, instructions to
                                * remove reply keyboard or to force a reply from the user.*/
                              replyMarkup: Option[KeyboardMarkup] = Option.empty)
