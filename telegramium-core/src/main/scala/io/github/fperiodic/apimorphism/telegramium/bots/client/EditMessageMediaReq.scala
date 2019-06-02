package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.ChatId
import io.github.fperiodic.apimorphism.telegramium.bots.InputMedia
import io.github.fperiodic.apimorphism.telegramium.bots.InlineKeyboardMarkup

final case class EditMessageMediaReq(
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
                                     /** A JSON-serialized object for a new media content of the
                                       * message*/
                                     media: InputMedia,
                                     /** A JSON-serialized object for a new inline keyboard.*/
                                     replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
