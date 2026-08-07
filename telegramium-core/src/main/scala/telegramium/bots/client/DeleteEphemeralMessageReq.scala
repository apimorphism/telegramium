package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup in the format &#064;username
  * @param receiverUserId
  *   Identifier of the user who received the message
  * @param ephemeralMessageId
  *   Identifier of the ephemeral message to delete
  */
final case class DeleteEphemeralMessageReq(chatId: ChatId, receiverUserId: Int, ephemeralMessageId: Int)
