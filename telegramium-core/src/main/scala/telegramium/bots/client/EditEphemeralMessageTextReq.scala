package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.LinkPreviewOptions
import telegramium.bots.InlineKeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup in the format &#064;username
  * @param receiverUserId
  *   Identifier of the user who received the message
  * @param ephemeralMessageId
  *   Identifier of the ephemeral message to edit
  * @param text
  *   New text of the message, 1-4096 characters after entity parsing
  * @param parseMode
  *   Mode for parsing entities in the message text. See formatting options for more details.
  * @param entities
  *   A JSON-serialized list of special entities that appear in message text, which can be specified instead of
  *   parse_mode
  * @param linkPreviewOptions
  *   Link preview generation options for the message
  * @param replyMarkup
  *   A JSON-serialized object for an inline keyboard
  */
final case class EditEphemeralMessageTextReq(
  chatId: ChatId,
  receiverUserId: Int,
  ephemeralMessageId: Int,
  text: String,
  parseMode: Option[ParseMode] = Option.empty,
  entities: List[MessageEntity] = List.empty,
  linkPreviewOptions: Option[LinkPreviewOptions] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
