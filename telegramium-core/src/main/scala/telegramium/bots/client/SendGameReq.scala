package telegramium.bots.client

import telegramium.bots.ReplyParameters
import telegramium.bots.InlineKeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat
  * @param gameShortName
  *   Short name of the game, serves as the unique identifier for the game. Set up your games via &#064;BotFather.
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message will be sent
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent message from forwarding and saving
  * @param allowPaidBroadcast
  *   Pass True to allow up to 1000 messages per second, ignoring broadcasting limits for a fee of 0.1 Telegram Stars
  *   per message. The relevant Stars will be withdrawn from the bot's balance
  * @param messageEffectId
  *   Unique identifier of the message effect to be added to the message; for private chats only
  * @param replyParameters
  *   Description of the message to reply to
  * @param replyMarkup
  *   A JSON-serialized object for an inline keyboard. If empty, one 'Play game_title' button will be shown. If not
  *   empty, the first button must launch the game.
  */
final case class SendGameReq(
  chatId: Long,
  gameShortName: String,
  businessConnectionId: Option[String] = Option.empty,
  messageThreadId: Option[Int] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  allowPaidBroadcast: Option[Boolean] = Option.empty,
  messageEffectId: Option[String] = Option.empty,
  replyParameters: Option[ReplyParameters] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
