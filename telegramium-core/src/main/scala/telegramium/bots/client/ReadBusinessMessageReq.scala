package telegramium.bots.client

/** @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which to read the message
  * @param chatId
  *   Unique identifier of the chat in which the message was received. The chat must have been active in the last 24
  *   hours.
  * @param messageId
  *   Unique identifier of the message to mark as read
  */
final case class ReadBusinessMessageReq(businessConnectionId: String, chatId: Long, messageId: Int)
