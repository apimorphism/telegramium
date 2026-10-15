package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel in the format <code>&#064;username</code>
  * @param messageId
  *   Identifier of a message to pin
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message will be pinned
  * @param disableNotification
  *   Pass <em>True</em> if it is not necessary to send a notification to all chat members about the new pinned message.
  *   Notifications are always disabled in channels and private chats.
  */
final case class PinChatMessageReq(
  chatId: ChatId,
  messageId: Int,
  businessConnectionId: Option[String] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty
)
