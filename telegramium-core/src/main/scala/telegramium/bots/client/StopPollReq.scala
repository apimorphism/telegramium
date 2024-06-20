package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InlineKeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param messageId
  *   Identifier of the original message with the poll
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message to be edited was sent
  * @param replyMarkup
  *   A JSON-serialized object for a new message inline keyboard.
  */
final case class StopPollReq(
  chatId: ChatId,
  messageId: Int,
  businessConnectionId: Option[String] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
