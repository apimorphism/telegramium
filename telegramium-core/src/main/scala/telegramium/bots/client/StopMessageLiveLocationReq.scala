package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InlineKeyboardMarkup

/** @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message to be edited was sent
  * @param chatId
  *   Required if <em>inline_message_id</em> is not specified. Unique identifier for the target chat or username of the
  *   target bot, supergroup or channel in the format <code>&#064;username</code>.
  * @param messageId
  *   Required if <em>inline_message_id</em> is not specified. Identifier of the message with live location to stop.
  * @param inlineMessageId
  *   Required if <em>chat_id</em> and <em>message_id</em> are not specified. Identifier of the inline message.
  * @param replyMarkup
  *   A JSON-serialized object for a new <a href="https://core.telegram.org/bots/features#inline-keyboards">inline
  *   keyboard</a>
  */
final case class StopMessageLiveLocationReq(
  businessConnectionId: Option[String] = Option.empty,
  chatId: Option[ChatId] = Option.empty,
  messageId: Option[Int] = Option.empty,
  inlineMessageId: Option[String] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
