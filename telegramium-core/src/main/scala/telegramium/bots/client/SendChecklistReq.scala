package telegramium.bots.client

import telegramium.bots.InputChecklist
import telegramium.bots.ReplyParameters
import telegramium.bots.InlineKeyboardMarkup

/** @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message will be sent
  * @param chatId
  *   Unique identifier for the target chat
  * @param checklist
  *   A JSON-serialized object for the checklist to send
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent message from forwarding and saving
  * @param messageEffectId
  *   Unique identifier of the message effect to be added to the message
  * @param replyParameters
  *   A JSON-serialized object for description of the message to reply to
  * @param replyMarkup
  *   A JSON-serialized object for an inline keyboard
  */
final case class SendChecklistReq(
  businessConnectionId: String,
  chatId: Long,
  checklist: InputChecklist,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  messageEffectId: Option[String] = Option.empty,
  replyParameters: Option[ReplyParameters] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
