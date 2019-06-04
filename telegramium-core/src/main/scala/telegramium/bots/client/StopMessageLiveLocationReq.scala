package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InlineKeyboardMarkup

final case class StopMessageLiveLocationReq(
    /** Required if inline_message_id is not specified. Unique
      * identifier for the target chat or username of the target
      * channel (in the format @channelusername)*/
    chatId: Option[ChatId] = Option.empty,
    /** Required if inline_message_id is not specified. Identifier
      * of the message with live location to stop*/
    messageId: Option[Int] = Option.empty,
    /** Required if chat_id and message_id are not specified.
      * Identifier of the inline message*/
    inlineMessageId: Option[String] = Option.empty,
    /** A JSON-serialized object for a new inline keyboard.*/
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
