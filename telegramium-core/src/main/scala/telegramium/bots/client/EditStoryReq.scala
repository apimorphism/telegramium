package telegramium.bots.client

import telegramium.bots.InputStoryContent
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.StoryArea

/** @param businessConnectionId
  *   Unique identifier of the business connection
  * @param storyId
  *   Unique identifier of the story to edit
  * @param content
  *   Content of the story
  * @param caption
  *   Caption of the story, 0-2048 characters after entities parsing
  * @param parseMode
  *   Mode for parsing entities in the story caption. See formatting options for more details.
  * @param captionEntities
  *   A JSON-serialized list of special entities that appear in the caption, which can be specified instead of
  *   parse_mode
  * @param areas
  *   A JSON-serialized list of clickable areas to be shown on the story
  */
final case class EditStoryReq(
  businessConnectionId: String,
  storyId: Int,
  content: InputStoryContent,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  areas: List[StoryArea] = List.empty
)
