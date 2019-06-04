package telegramium.bots.client

import telegramium.bots.InlineKeyboardMarkup

final case class SendGameReq(
                             /** Unique identifier for the target chat*/
                             chatId: Int,
                             /** Short name of the game, serves as the unique identifier for
                               * the game. Set up your games via Botfather.*/
                             gameShortName: String,
                             /** Sends the message silently. Users will receive a
                               * notification with no sound.*/
                             disableNotification: Option[Boolean] = Option.empty,
                             /** If the message is a reply, ID of the original message*/
                             replyToMessageId: Option[Int] = Option.empty,
                             /** A JSON-serialized object for an inline keyboard. If empty,
                               * one ‘Play game_title’ button will be shown. If not empty,
                               * the first button must launch the game.*/
                             replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
