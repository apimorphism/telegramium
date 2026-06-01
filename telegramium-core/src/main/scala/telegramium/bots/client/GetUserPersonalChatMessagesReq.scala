package telegramium.bots.client

/** @param userId
  *   Unique identifier for the target user
  * @param limit
  *   The maximum number of messages to return; 1-20
  */
final case class GetUserPersonalChatMessagesReq(userId: Long, limit: Int)
