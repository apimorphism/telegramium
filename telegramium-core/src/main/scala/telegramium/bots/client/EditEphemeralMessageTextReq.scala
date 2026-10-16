package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.InputRichMessage
import telegramium.bots.LinkPreviewOptions
import telegramium.bots.InlineKeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup in the format
  *   <code>&#064;username</code>
  * @param receiverUserId
  *   Identifier of the user who received the message
  * @param ephemeralMessageId
  *   Identifier of the ephemeral message to edit
  * @param text
  *   New text of the message, 1-4096 characters after entity parsing; required if <em>rich_message</em> isn't specified
  * @param parseMode
  *   Mode for parsing entities in the message text. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param entities
  *   A JSON-serialized list of special entities that appear in message text, which can be specified instead of
  *   <em>parse_mode</em>
  * @param richMessage
  *   New rich content of the message; required if <em>text</em> isn't specified
  * @param linkPreviewOptions
  *   Link preview generation options for the message
  * @param replyMarkup
  *   A JSON-serialized object for an <a href="https://core.telegram.org/bots/features#inline-keyboards">inline
  *   keyboard</a>
  */
final case class EditEphemeralMessageTextReq(
  chatId: ChatId,
  receiverUserId: Long,
  ephemeralMessageId: Int,
  text: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  entities: List[MessageEntity] = List.empty,
  richMessage: Option[InputRichMessage] = Option.empty,
  linkPreviewOptions: Option[LinkPreviewOptions] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
