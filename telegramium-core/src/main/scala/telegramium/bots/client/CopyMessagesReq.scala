package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target bot, supergroup or channel in the format
  *   <code>&#064;username</code>
  * @param fromChatId
  *   Unique identifier for the chat where the original messages were sent (or username of the target bot, supergroup or
  *   channel in the format <code>&#064;username</code>)
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of a forum; for forum supergroups and private chats of
  *   bots with forum topic mode enabled only
  * @param directMessagesTopicId
  *   Identifier of the direct messages topic to which the messages will be sent; required if the messages are sent to a
  *   direct messages chat
  * @param messageIds
  *   A JSON-serialized list of 1-100 identifiers of messages in the chat <em>from_chat_id</em> to copy. The identifiers
  *   must be specified in a strictly increasing order.
  * @param disableNotification
  *   Sends the messages <a href="https://telegram.org/blog/channels-2-0#silent-messages">silently</a>. Users will
  *   receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent messages from forwarding and saving
  * @param removeCaption
  *   Pass <em>True</em> to copy the messages without their captions
  */
final case class CopyMessagesReq(
  chatId: ChatId,
  fromChatId: ChatId,
  messageThreadId: Option[Int] = Option.empty,
  directMessagesTopicId: Option[Long] = Option.empty,
  messageIds: List[Int] = List.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  removeCaption: Option[Boolean] = Option.empty
)
