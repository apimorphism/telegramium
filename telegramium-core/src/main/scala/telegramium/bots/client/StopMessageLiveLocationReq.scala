package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InlineKeyboardMarkup

/**
 * @param chatId Required if inline_message_id is not specified. Unique
 * identifier for the target chat or username of the target
 * channel (in the format &#064;channelusername)
 * @param messageId Required if inline_message_id is not specified. Identifier
 * of the message with live location to stop
 * @param inlineMessageId Required if chat_id and message_id are not specified.
 * Identifier of the inline message
 * @param replyMarkup A JSON-serialized object for a new inline keyboard.
 */
final case class StopMessageLiveLocationReq(chatId: Option[ChatId] = Option.empty,
                                            messageId: Option[Int] = Option.empty,
                                            inlineMessageId: Option[String] = Option.empty,
                                            replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
