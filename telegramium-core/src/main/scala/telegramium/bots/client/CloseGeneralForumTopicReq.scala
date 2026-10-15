package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup in the format
  *   <code>&#064;username</code>
  */
final case class CloseGeneralForumTopicReq(chatId: ChatId)
