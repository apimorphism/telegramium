package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InputChecklist
import telegramium.bots.InlineKeyboardMarkup

/** @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message will be sent
  * @param chatId
  *   Unique identifier for the target chat or username of the target bot in the format <code>&#064;username</code>
  * @param messageId
  *   Unique identifier for the target message
  * @param checklist
  *   A JSON-serialized object for the new checklist
  * @param replyMarkup
  *   A JSON-serialized object for the new <a href="https://core.telegram.org/bots/features#inline-keyboards">inline
  *   keyboard</a> for the message
  */
final case class EditMessageChecklistReq(
  businessConnectionId: String,
  chatId: ChatId,
  messageId: Int,
  checklist: InputChecklist,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
