package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InlineKeyboardMarkup

final case class EditMessageReplyMarkupReq(
                                           /** Required if inline_message_id is not specified. Unique
                                             * identifier for the target chat or username of the target
                                             * channel (in the format &#064;channelusername)*/
                                           chatId: Option[ChatId] = Option.empty,
                                           /** Required if inline_message_id is not specified. Identifier
                                             * of the message to edit*/
                                           messageId: Option[Int] = Option.empty,
                                           /** Required if chat_id and message_id are not specified.
                                             * Identifier of the inline message*/
                                           inlineMessageId: Option[String] = Option.empty,
                                           /** A JSON-serialized object for an inline keyboard.*/
                                           replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
