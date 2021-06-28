package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup (in the format
  *   &#064;supergroupusername)
  * @param userId
  *   Unique identifier of the target user
  * @param customTitle
  *   New custom title for the administrator; 0-16 characters, emoji are not allowed
  */
final case class SetChatAdministratorCustomTitleReq(chatId: ChatId, userId: Long, customTitle: String)
