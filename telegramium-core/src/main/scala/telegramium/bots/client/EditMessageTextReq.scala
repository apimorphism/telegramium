package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.LinkPreviewOptions
import telegramium.bots.InputRichMessage
import telegramium.bots.InlineKeyboardMarkup

/** @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message to be edited was sent
  * @param chatId
  *   Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target
  *   bot, supergroup or channel in the format &#064;username.
  * @param messageId
  *   Required if inline_message_id is not specified. Identifier of the message to edit.
  * @param inlineMessageId
  *   Required if chat_id and message_id are not specified. Identifier of the inline message.
  * @param text
  *   New text of the message, 1-4096 characters after entity parsing; required if rich_message isn't specified
  * @param parseMode
  *   Mode for parsing entities in the message text. See formatting options for more details.
  * @param entities
  *   A JSON-serialized list of special entities that appear in message text, which can be specified instead of
  *   parse_mode
  * @param linkPreviewOptions
  *   Link preview generation options for the message
  * @param richMessage
  *   New rich content of the message; required if text isn't specified. Direct upload of new files isn't supported when
  *   an inline message is edited.
  * @param replyMarkup
  *   A JSON-serialized object for an inline keyboard
  */
final case class EditMessageTextReq(
  businessConnectionId: Option[String] = Option.empty,
  chatId: Option[ChatId] = Option.empty,
  messageId: Option[Int] = Option.empty,
  inlineMessageId: Option[String] = Option.empty,
  text: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  entities: List[MessageEntity] = List.empty,
  linkPreviewOptions: Option[LinkPreviewOptions] = Option.empty,
  richMessage: Option[InputRichMessage] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
