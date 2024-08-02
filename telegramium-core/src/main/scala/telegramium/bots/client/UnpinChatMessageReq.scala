package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message will be unpinned
  * @param messageId
  *   Identifier of the message to unpin. Required if business_connection_id is specified. If not specified, the most
  *   recent pinned message (by sending date) will be unpinned.
  */
final case class UnpinChatMessageReq(
  chatId: ChatId,
  businessConnectionId: Option[String] = Option.empty,
  messageId: Option[Int] = Option.empty
)
