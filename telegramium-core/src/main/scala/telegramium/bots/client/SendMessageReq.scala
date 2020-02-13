package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.KeyboardMarkup

final case class SendMessageReq(
                                /** Unique identifier for the target chat or username of the
                                  * target channel (in the format @channelusername)*/
                                chatId: ChatId,
                                /** Text of the message to be sent, 1-4096 characters after
                                  * entities parsing*/
                                text: String,
                                /** Send Markdown or HTML, if you want Telegram apps to show
                                  * bold, italic, fixed-width text or inline URLs in your bot's
                                  * message.*/
                                parseMode: Option[String] = Option.empty,
                                /** Disables link previews for links in this message*/
                                disableWebPagePreview: Option[Boolean] = Option.empty,
                                /** Sends the message silently. Users will receive a
                                  * notification with no sound.*/
                                disableNotification: Option[Boolean] = Option.empty,
                                /** If the message is a reply, ID of the original message*/
                                replyToMessageId: Option[Int] = Option.empty,
                                /** Additional interface options. A JSON-serialized object for
                                  * an inline keyboard, custom reply keyboard, instructions to
                                  * remove reply keyboard or to force a reply from the user.*/
                                replyMarkup: Option[KeyboardMarkup] = Option.empty)
