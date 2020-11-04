package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.InlineKeyboardMarkup

final case class EditMessageTextReq(
                                    /** Required if inline_message_id is not specified. Unique
                                      * identifier for the target chat or username of the target
                                      * channel (in the format @channelusername)*/
                                    chatId: Option[ChatId] = Option.empty,
                                    /** Required if inline_message_id is not specified. Identifier
                                      * of the message to edit*/
                                    messageId: Option[Int] = Option.empty,
                                    /** Required if chat_id and message_id are not specified.
                                      * Identifier of the inline message*/
                                    inlineMessageId: Option[String] = Option.empty,
                                    /** New text of the message, 1-4096 characters after entities
                                      * parsing*/
                                    text: String,
                                    /** Mode for parsing entities in the message text. See
                                      * formatting options for more details.*/
                                    parseMode: Option[ParseMode] = Option.empty,
                                    /** List of special entities that appear in message text, which
                                      * can be specified instead of parse_mode*/
                                    entities: List[MessageEntity] = List.empty,
                                    /** Disables link previews for links in this message*/
                                    disableWebPagePreview: Option[Boolean] = Option.empty,
                                    /** A JSON-serialized object for an inline keyboard.*/
                                    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
