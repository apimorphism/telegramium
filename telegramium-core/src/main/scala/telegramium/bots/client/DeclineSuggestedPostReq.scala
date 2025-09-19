package telegramium.bots.client

/** @param chatId
  *   Unique identifier for the target direct messages chat
  * @param messageId
  *   Identifier of a suggested post message to decline
  * @param comment
  *   Comment for the creator of the suggested post; 0-128 characters
  */
final case class DeclineSuggestedPostReq(chatId: Long, messageId: Int, comment: Option[String] = Option.empty)
