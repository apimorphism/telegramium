package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param messageIds
  *   A JSON-serialized list of 1-100 identifiers of messages to delete. See deleteMessage for limitations on which
  *   messages can be deleted
  */
final case class DeleteMessagesReq(chatId: ChatId, messageIds: List[Int] = List.empty)
