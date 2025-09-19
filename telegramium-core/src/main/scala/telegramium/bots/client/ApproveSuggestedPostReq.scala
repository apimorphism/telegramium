package telegramium.bots.client

/** @param chatId
  *   Unique identifier for the target direct messages chat
  * @param messageId
  *   Identifier of a suggested post message to approve
  * @param sendDate
  *   Point in time (Unix timestamp) when the post is expected to be published; omit if the date has already been
  *   specified when the suggested post was created. If specified, then the date must be not more than 2678400 seconds
  *   (30 days) in the future
  */
final case class ApproveSuggestedPostReq(chatId: Long, messageId: Int, sendDate: Option[Long] = Option.empty)
