package telegramium.bots.client

import telegramium.bots.InputChecklist
import telegramium.bots.InlineKeyboardMarkup

/** @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message will be sent
  * @param chatId
  *   Unique identifier for the target chat
  * @param messageId
  *   Unique identifier for the target message
  * @param checklist
  *   A JSON-serialized object for the new checklist
  * @param replyMarkup
  *   A JSON-serialized object for the new inline keyboard for the message
  */
final case class EditMessageChecklistReq(
  businessConnectionId: String,
  chatId: Long,
  messageId: Int,
  checklist: InputChecklist,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
