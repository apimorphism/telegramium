package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.KeyboardMarkup

final case class SendMessageReq(
                                /** Unique identifier for the target chat or username of the
                                  * target channel (in the format &#064;channelusername)*/
                                chatId: ChatId,
                                /** Text of the message to be sent, 1-4096 characters after
                                  * entities parsing*/
                                text: String,
                                /** Mode for parsing entities in the message text. See
                                  * formatting options for more details.*/
                                parseMode: Option[ParseMode] = Option.empty,
                                /** List of special entities that appear in message text, which
                                  * can be specified instead of parse_mode*/
                                entities: List[MessageEntity] = List.empty,
                                /** Disables link previews for links in this message*/
                                disableWebPagePreview: Option[Boolean] = Option.empty,
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
