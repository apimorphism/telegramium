package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.ChatId
import io.github.fperiodic.apimorphism.telegramium.bots.InlineKeyboardMarkup

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
                                    /** New text of the message*/
                                    text: String,
                                    /** Send Markdown or HTML, if you want Telegram apps to show
                                      * bold, italic, fixed-width text or inline URLs in your bot's
                                      * message.*/
                                    parseMode: Option[String] = Option.empty,
                                    /** Disables link previews for links in this message*/
                                    disableWebPagePreview: Option[Boolean] = Option.empty,
                                    /** A JSON-serialized object for an inline keyboard.*/
                                    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
