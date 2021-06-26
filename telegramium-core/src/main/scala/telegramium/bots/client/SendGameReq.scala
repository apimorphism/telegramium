package telegramium.bots.client

import telegramium.bots.InlineKeyboardMarkup

/** @param chatId Unique identifier for the target chat
  * @param gameShortName Short name of the game, serves as the unique identifier for
  * the game. Set up your games via Botfather.
  * @param disableNotification Sends the message silently. Users will receive a
  * notification with no sound.
  * @param replyToMessageId If the message is a reply, ID of the original message
  * @param allowSendingWithoutReply Pass True, if the message should be sent even if the
  * specified replied-to message is not found
  * @param replyMarkup A JSON-serialized object for an inline keyboard. If empty,
  * one 'Play game_title' button will be shown. If not empty,
  * the first button must launch the game. */
final case class SendGameReq(chatId: Int,
                             gameShortName: String,
                             disableNotification: Option[Boolean] = Option.empty,
                             replyToMessageId: Option[Int] = Option.empty,
                             allowSendingWithoutReply: Option[Boolean] = Option.empty,
                             replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
