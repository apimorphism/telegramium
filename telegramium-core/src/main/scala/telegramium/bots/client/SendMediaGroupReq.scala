package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InputMedia
import telegramium.bots.ReplyParameters

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message will be sent
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of a forum; for forum supergroups and private chats of
  *   bots with forum topic mode enabled only
  * @param directMessagesTopicId
  *   Identifier of the direct messages topic to which the messages will be sent; required if the messages are sent to a
  *   direct messages chat
  * @param media
  *   A JSON-serialized array describing messages to be sent, must include 2-10 items
  * @param disableNotification
  *   Sends messages silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent messages from forwarding and saving
  * @param allowPaidBroadcast
  *   Pass True to allow up to 1000 messages per second, ignoring broadcasting limits for a fee of 0.1 Telegram Stars
  *   per message. The relevant Stars will be withdrawn from the bot's balance
  * @param messageEffectId
  *   Unique identifier of the message effect to be added to the message; for private chats only
  * @param replyParameters
  *   Description of the message to reply to
  */
final case class SendMediaGroupReq(
  chatId: ChatId,
  businessConnectionId: Option[String] = Option.empty,
  messageThreadId: Option[Int] = Option.empty,
  directMessagesTopicId: Option[Long] = Option.empty,
  media: List[InputMedia] = List.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  allowPaidBroadcast: Option[Boolean] = Option.empty,
  messageEffectId: Option[String] = Option.empty,
  replyParameters: Option[ReplyParameters] = Option.empty
)
