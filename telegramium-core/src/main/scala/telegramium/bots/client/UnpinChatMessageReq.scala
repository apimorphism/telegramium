package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId Unique identifier for the target chat or username of the
  * target channel (in the format &#064;channelusername)
  * @param messageId Identifier of a message to unpin. If not specified, the
  * most recent pinned message (by sending date) will be
  * unpinned. */
final case class UnpinChatMessageReq(chatId: ChatId, messageId: Option[Int] = Option.empty)
