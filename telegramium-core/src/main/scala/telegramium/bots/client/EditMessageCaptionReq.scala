package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.InlineKeyboardMarkup

/** @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message to be edited was sent
  * @param chatId
  *   Required if <em>inline_message_id</em> is not specified. Unique identifier for the target chat or username of the
  *   target bot, supergroup or channel in the format <code>&#064;username</code>.
  * @param messageId
  *   Required if <em>inline_message_id</em> is not specified. Identifier of the message to edit.
  * @param inlineMessageId
  *   Required if <em>chat_id</em> and <em>message_id</em> are not specified. Identifier of the inline message.
  * @param caption
  *   New caption of the message, 0-1024 characters after entities parsing
  * @param parseMode
  *   Mode for parsing entities in the message caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   A JSON-serialized list of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param showCaptionAboveMedia
  *   Pass <em>True</em> if the caption must be shown above the message media. Supported only for animation, photo and
  *   video messages.
  * @param replyMarkup
  *   A JSON-serialized object for an <a href="https://core.telegram.org/bots/features#inline-keyboards">inline
  *   keyboard</a>
  */
final case class EditMessageCaptionReq(
  businessConnectionId: Option[String] = Option.empty,
  chatId: Option[ChatId] = Option.empty,
  messageId: Option[Int] = Option.empty,
  inlineMessageId: Option[String] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
