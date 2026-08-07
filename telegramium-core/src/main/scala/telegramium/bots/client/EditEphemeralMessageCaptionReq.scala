package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.InlineKeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup in the format &#064;username
  * @param receiverUserId
  *   Identifier of the user who received the message
  * @param ephemeralMessageId
  *   Identifier of the ephemeral message to edit
  * @param caption
  *   New caption of the message, 0-1024 characters after entities parsing
  * @param parseMode
  *   Mode for parsing entities in the message caption. See formatting options for more details.
  * @param captionEntities
  *   A JSON-serialized list of special entities that appear in the caption, which can be specified instead of
  *   parse_mode
  * @param replyMarkup
  *   A JSON-serialized object for an inline keyboard
  */
final case class EditEphemeralMessageCaptionReq(
  chatId: ChatId,
  receiverUserId: Int,
  ephemeralMessageId: Int,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
