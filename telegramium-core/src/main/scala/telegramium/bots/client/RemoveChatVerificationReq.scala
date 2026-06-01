package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target bot or channel in the format &#064;username
  */
final case class RemoveChatVerificationReq(chatId: ChatId)
