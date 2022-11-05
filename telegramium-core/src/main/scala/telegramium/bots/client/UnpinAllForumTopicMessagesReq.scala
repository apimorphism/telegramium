package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup (in the format
  *   &#064;supergroupusername)
  * @param messageThreadId
  *   Unique identifier for the target message thread of the forum topic
  */
final case class UnpinAllForumTopicMessagesReq(chatId: ChatId, messageThreadId: Int)
