package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param messageId
  *   Identifier of a message to pin
  * @param disableNotification
  *   Pass True, if it is not necessary to send a notification to all chat members about the new pinned message.
  *   Notifications are always disabled in channels and private chats.
  */
final case class PinChatMessageReq(chatId: ChatId, messageId: Int, disableNotification: Option[Boolean] = Option.empty)
