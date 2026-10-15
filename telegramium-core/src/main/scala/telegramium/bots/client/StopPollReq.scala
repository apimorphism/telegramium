package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InlineKeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target bot, supergroup or channel in the format
  *   <code>&#064;username</code>
  * @param messageId
  *   Identifier of the original message with the poll
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message to be edited was sent
  * @param replyMarkup
  *   A JSON-serialized object for a new message <a
  *   href="https://core.telegram.org/bots/features#inline-keyboards">inline keyboard</a>
  */
final case class StopPollReq(
  chatId: ChatId,
  messageId: Int,
  businessConnectionId: Option[String] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
