package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.InlineKeyboardMarkup

/** @param chatId Required if inline_message_id is not specified. Unique
  * identifier for the target chat or username of the target
  * channel (in the format &#064;channelusername)
  * @param messageId Required if inline_message_id is not specified. Identifier
  * of the message to edit
  * @param inlineMessageId Required if chat_id and message_id are not specified.
  * Identifier of the inline message
  * @param text New text of the message, 1-4096 characters after entities
  * parsing
  * @param parseMode Mode for parsing entities in the message text. See
  * formatting options for more details.
  * @param entities List of special entities that appear in message text, which
  * can be specified instead of parse_mode
  * @param disableWebPagePreview Disables link previews for links in this message
  * @param replyMarkup A JSON-serialized object for an inline keyboard. */
final case class EditMessageTextReq(chatId: Option[ChatId] = Option.empty,
                                    messageId: Option[Int] = Option.empty,
                                    inlineMessageId: Option[String] = Option.empty,
                                    text: String,
                                    parseMode: Option[ParseMode] = Option.empty,
                                    entities: List[MessageEntity] = List.empty,
                                    disableWebPagePreview: Option[Boolean] = Option.empty,
                                    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
