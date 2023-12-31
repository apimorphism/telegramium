package telegramium.bots.client

import telegramium.bots.ReplyParameters
import telegramium.bots.InlineKeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat
  * @param gameShortName
  *   Short name of the game, serves as the unique identifier for the game. Set up your games via &#064;BotFather.
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent message from forwarding and saving
  * @param replyParameters
  *   Description of the message to reply to
  * @param replyMarkup
  *   A JSON-serialized object for an inline keyboard. If empty, one 'Play game_title' button will be shown. If not
  *   empty, the first button must launch the game.
  */
final case class SendGameReq(
  chatId: Long,
  gameShortName: String,
  messageThreadId: Option[Int] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  replyParameters: Option[ReplyParameters] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
