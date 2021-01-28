package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InputMedia
import telegramium.bots.InlineKeyboardMarkup

/** @param chatId Required if inline_message_id is not specified. Unique
  * identifier for the target chat or username of the target
  * channel (in the format &#064;channelusername)
  * @param messageId Required if inline_message_id is not specified. Identifier
  * of the message to edit
  * @param inlineMessageId Required if chat_id and message_id are not specified.
  * Identifier of the inline message
  * @param media A JSON-serialized object for a new media content of the
  * message
  * @param replyMarkup A JSON-serialized object for a new inline keyboard. */
final case class EditMessageMediaReq(chatId: Option[ChatId] = Option.empty,
                                     messageId: Option[Int] = Option.empty,
                                     inlineMessageId: Option[String] = Option.empty,
                                     media: InputMedia,
                                     replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
