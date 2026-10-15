package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InlineKeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup in the format
  *   <code>&#064;username</code>
  * @param receiverUserId
  *   Identifier of the user who received the message
  * @param ephemeralMessageId
  *   Identifier of the ephemeral message to edit
  * @param replyMarkup
  *   A JSON-serialized object for an <a href="https://core.telegram.org/bots/features#inline-keyboards">inline
  *   keyboard</a>
  */
final case class EditEphemeralMessageReplyMarkupReq(
  chatId: ChatId,
  receiverUserId: Long,
  ephemeralMessageId: Int,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
