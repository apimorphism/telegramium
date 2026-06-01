package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup in the format &#064;username
  * @param userId
  *   Identifier of the user whose reactions will be removed, if the reactions were added by a user
  * @param actorChatId
  *   Identifier of the chat whose reactions will be removed, if the reactions were added by a chat
  */
final case class DeleteAllMessageReactionsReq(
  chatId: ChatId,
  userId: Option[Long] = Option.empty,
  actorChatId: Option[Long] = Option.empty
)
