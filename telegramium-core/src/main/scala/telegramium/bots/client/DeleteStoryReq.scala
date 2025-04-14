package telegramium.bots.client

/** @param businessConnectionId
  *   Unique identifier of the business connection
  * @param storyId
  *   Unique identifier of the story to delete
  */
final case class DeleteStoryReq(businessConnectionId: String, storyId: Int)
