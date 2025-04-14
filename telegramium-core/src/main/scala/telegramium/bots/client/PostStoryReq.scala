package telegramium.bots.client

import telegramium.bots.InputStoryContent
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.StoryArea

/** @param businessConnectionId
  *   Unique identifier of the business connection
  * @param content
  *   Content of the story
  * @param activePeriod
  *   Period after which the story is moved to the archive, in seconds; must be one of 6 * 3600, 12 * 3600, 86400, or 2
  *   * 86400
  * @param caption
  *   Caption of the story, 0-2048 characters after entities parsing
  * @param parseMode
  *   Mode for parsing entities in the story caption. See formatting options for more details.
  * @param captionEntities
  *   A JSON-serialized list of special entities that appear in the caption, which can be specified instead of
  *   parse_mode
  * @param areas
  *   A JSON-serialized list of clickable areas to be shown on the story
  * @param postToChatPage
  *   Pass True to keep the story accessible after it expires
  * @param protectContent
  *   Pass True if the content of the story must be protected from forwarding and screenshotting
  */
final case class PostStoryReq(
  businessConnectionId: String,
  content: InputStoryContent,
  activePeriod: Int,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  areas: List[StoryArea] = List.empty,
  postToChatPage: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty
)
