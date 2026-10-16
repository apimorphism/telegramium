package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target bot, supergroup or channel in the format
  *   <code>&#064;username</code>
  * @param messageIds
  *   A JSON-serialized list of 1-100 identifiers of messages to delete. See <a
  *   href="https://core.telegram.org/bots/api#deletemessage">deleteMessage</a> for limitations on which messages can be
  *   deleted.
  */
final case class DeleteMessagesReq(chatId: ChatId, messageIds: List[Int] = List.empty)
