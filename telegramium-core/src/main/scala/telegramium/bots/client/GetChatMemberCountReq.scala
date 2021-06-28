package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup or channel (in the format
  *   &#064;channelusername)
  */
final case class GetChatMemberCountReq(chatId: ChatId)
