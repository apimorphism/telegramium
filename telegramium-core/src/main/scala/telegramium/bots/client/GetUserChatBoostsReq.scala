package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the chat or username of the channel (in the format &#064;channelusername)
  * @param userId
  *   Unique identifier of the target user
  */
final case class GetUserChatBoostsReq(chatId: ChatId, userId: Long)
