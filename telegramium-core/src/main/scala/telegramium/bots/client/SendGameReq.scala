package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ReplyParameters
import telegramium.bots.InlineKeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target bot in the format <code>&#064;username</code>.
  *   Games can't be sent to channel direct messages chats and channel chats.
  * @param gameShortName
  *   Short name of the game, serves as the unique identifier for the game. Set up your games via <a
  *   href="https://t.me/botfather">&#064;BotFather</a>.
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message will be sent
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of a forum; for forum supergroups and private chats of
  *   bots with forum topic mode enabled only
  * @param disableNotification
  *   Sends the message <a href="https://telegram.org/blog/channels-2-0#silent-messages">silently</a>. Users will
  *   receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent message from forwarding and saving
  * @param allowPaidBroadcast
  *   Pass <em>True</em> to allow up to 1000 messages per second, ignoring <a
  *   href="https://core.telegram.org/bots/faq#how-can-i-message-all-of-my-bot-39s-subscribers-at-once">broadcasting
  *   limits</a> for a fee of 0.1 Telegram Stars per message. The relevant Stars will be withdrawn from the bot's
  *   balance.
  * @param messageEffectId
  *   Unique identifier of the message effect to be added to the message; for private chats only
  * @param replyParameters
  *   Description of the message to reply to
  * @param replyMarkup
  *   A JSON-serialized object for an <a href="https://core.telegram.org/bots/features#inline-keyboards">inline
  *   keyboard</a>. If empty, one 'Play game_title' button will be shown. If not empty, the first button must launch the
  *   game.
  */
final case class SendGameReq(
  chatId: ChatId,
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
