package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InputMedia
import telegramium.bots.InlineKeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup in the format &#064;username
  * @param receiverUserId
  *   Identifier of the user who received the message
  * @param ephemeralMessageId
  *   Identifier of the ephemeral message to edit
  * @param media
  *   A JSON-serialized object for the new media content of the message. A new file can't be uploaded; use a previously
  *   uploaded file via its file_id or specify a URL.
  * @param replyMarkup
  *   A JSON-serialized object for an inline keyboard
  */
final case class EditEphemeralMessageMediaReq(
  chatId: ChatId,
  receiverUserId: Int,
  ephemeralMessageId: Int,
  media: InputMedia,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
