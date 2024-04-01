package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param fromChatId
  *   Unique identifier for the chat where the original messages were sent (or channel username in the format
  *   &#064;channelusername)
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
  * @param messageIds
  *   A JSON-serialized list of 1-100 identifiers of messages in the chat from_chat_id to copy. The identifiers must be
  *   specified in a strictly increasing order.
  * @param disableNotification
  *   Sends the messages silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent messages from forwarding and saving
  * @param removeCaption
  *   Pass True to copy the messages without their captions
  */
final case class CopyMessagesReq(
  chatId: ChatId,
  fromChatId: ChatId,
  messageThreadId: Option[Int] = Option.empty,
  messageIds: List[Int] = List.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  removeCaption: Option[Boolean] = Option.empty
)
