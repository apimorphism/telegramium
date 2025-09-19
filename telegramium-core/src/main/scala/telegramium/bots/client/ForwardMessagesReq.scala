package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param fromChatId
  *   Unique identifier for the chat where the original messages were sent (or channel username in the format
  *   &#064;channelusername)
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
  * @param directMessagesTopicId
  *   Identifier of the direct messages topic to which the messages will be forwarded; required if the messages are
  *   forwarded to a direct messages chat
  * @param messageIds
  *   A JSON-serialized list of 1-100 identifiers of messages in the chat from_chat_id to forward. The identifiers must
  *   be specified in a strictly increasing order.
  * @param disableNotification
  *   Sends the messages silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the forwarded messages from forwarding and saving
  */
final case class ForwardMessagesReq(
  chatId: ChatId,
  fromChatId: ChatId,
  messageThreadId: Option[Int] = Option.empty,
  directMessagesTopicId: Option[Long] = Option.empty,
  messageIds: List[Int] = List.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty
)
