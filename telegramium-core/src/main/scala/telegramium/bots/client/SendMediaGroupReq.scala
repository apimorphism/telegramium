package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InputMedia
import telegramium.bots.ReplyParameters

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message will be sent
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
  * @param media
  *   A JSON-serialized array describing messages to be sent, must include 2-10 items
  * @param disableNotification
  *   Sends messages silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent messages from forwarding and saving
  * @param messageEffectId
  *   Unique identifier of the message effect to be added to the message; for private chats only
  * @param replyParameters
  *   Description of the message to reply to
  */
final case class SendMediaGroupReq(
  chatId: ChatId,
  businessConnectionId: Option[String] = Option.empty,
  messageThreadId: Option[Int] = Option.empty,
  media: List[InputMedia] = List.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  messageEffectId: Option[String] = Option.empty,
  replyParameters: Option[ReplyParameters] = Option.empty
)
