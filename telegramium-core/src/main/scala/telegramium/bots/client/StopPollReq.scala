package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InlineKeyboardMarkup

final case class StopPollReq(
                             /** Unique identifier for the target chat or username of the
                               * target channel (in the format &#064;channelusername)*/
                             chatId: ChatId,
                             /** Identifier of the original message with the poll*/
                             messageId: Int,
                             /** A JSON-serialized object for a new message inline keyboard.*/
                             replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
