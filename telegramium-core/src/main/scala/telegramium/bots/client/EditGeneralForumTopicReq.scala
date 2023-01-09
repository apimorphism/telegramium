package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup (in the format
  *   &#064;supergroupusername)
  * @param name
  *   New topic name, 1-128 characters
  */
final case class EditGeneralForumTopicReq(chatId: ChatId, name: String)
