package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.InlineKeyboardMarkup

final case class EditMessageCaptionReq(
                                       /** Required if inline_message_id is not specified. Unique
                                         * identifier for the target chat or username of the target
                                         * channel (in the format &#064;channelusername)*/
                                       chatId: Option[ChatId] = Option.empty,
                                       /** Required if inline_message_id is not specified. Identifier
                                         * of the message to edit*/
                                       messageId: Option[Int] = Option.empty,
                                       /** Required if chat_id and message_id are not specified.
                                         * Identifier of the inline message*/
                                       inlineMessageId: Option[String] = Option.empty,
                                       /** New caption of the message, 0-1024 characters after
                                         * entities parsing*/
                                       caption: Option[String] = Option.empty,
                                       /** Mode for parsing entities in the message caption. See
                                         * formatting options for more details.*/
                                       parseMode: Option[ParseMode] = Option.empty,
                                       /** List of special entities that appear in the caption, which
                                         * can be specified instead of parse_mode*/
                                       captionEntities: List[MessageEntity] = List.empty,
                                       /** A JSON-serialized object for an inline keyboard.*/
                                       replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
