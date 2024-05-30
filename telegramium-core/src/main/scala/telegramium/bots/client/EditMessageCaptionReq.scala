package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.InlineKeyboardMarkup

/** @param chatId
  *   Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target
  *   channel (in the format &#064;channelusername)
  * @param messageId
  *   Required if inline_message_id is not specified. Identifier of the message to edit
  * @param inlineMessageId
  *   Required if chat_id and message_id are not specified. Identifier of the inline message
  * @param caption
  *   New caption of the message, 0-1024 characters after entities parsing
  * @param parseMode
  *   Mode for parsing entities in the message caption. See formatting options for more details.
  * @param captionEntities
  *   A JSON-serialized list of special entities that appear in the caption, which can be specified instead of
  *   parse_mode
  * @param showCaptionAboveMedia
  *   Pass True, if the caption must be shown above the message media. Supported only for animation, photo and video
  *   messages.
  * @param replyMarkup
  *   A JSON-serialized object for an inline keyboard.
  */
final case class EditMessageCaptionReq(
  chatId: Option[ChatId] = Option.empty,
  messageId: Option[Int] = Option.empty,
  inlineMessageId: Option[String] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
