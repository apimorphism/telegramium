package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup in the format &#064;username
  * @param messageId
  *   Identifier of the target message
  * @param userId
  *   Identifier of the user whose reaction will be removed, if the reaction was added by a user
  * @param actorChatId
  *   Identifier of the chat whose reaction will be removed, if the reaction was added by a chat
  */
final case class DeleteMessageReactionReq(
  chatId: ChatId,
  messageId: Int,
  userId: Option[Long] = Option.empty,
  actorChatId: Option[Long] = Option.empty
)
