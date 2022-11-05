package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param fromChatId
  *   Unique identifier for the chat where the original message was sent (or channel username in the format
  *   &#064;channelusername)
  * @param messageId
  *   Message identifier in the chat specified in from_chat_id
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the forwarded message from forwarding and saving
  */
final case class ForwardMessageReq(
  chatId: ChatId,
  fromChatId: ChatId,
  messageId: Int,
  messageThreadId: Option[Int] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty
)
