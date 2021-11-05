package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param userId
  *   Unique identifier of the target user
  */
final case class ApproveChatJoinRequestReq(chatId: ChatId, userId: Long)
