package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InlineKeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param messageId
  *   Identifier of the original message with the poll
  * @param replyMarkup
  *   A JSON-serialized object for a new message inline keyboard.
  */
final case class StopPollReq(chatId: ChatId, messageId: Int, replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
