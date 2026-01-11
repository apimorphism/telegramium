package telegramium.bots.client

/** @param businessConnectionId
  *   Unique identifier of the business connection
  * @param fromChatId
  *   Unique identifier of the chat which posted the story that should be reposted
  * @param fromStoryId
  *   Unique identifier of the story that should be reposted
  * @param activePeriod
  *   Period after which the story is moved to the archive, in seconds; must be one of 6 * 3600, 12 * 3600, 86400, or 2
  *   * 86400
  * @param postToChatPage
  *   Pass True to keep the story accessible after it expires
  * @param protectContent
  *   Pass True if the content of the story must be protected from forwarding and screenshotting
  */
final case class RepostStoryReq(
  businessConnectionId: String,
  fromChatId: Int,
  fromStoryId: Int,
  activePeriod: Int,
  postToChatPage: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty
)
