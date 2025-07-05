package telegramium.bots

/** Describes a task to add to a checklist.
  *
  * @param id
  *   Unique identifier of the task; must be positive and unique among all task identifiers currently present in the
  *   checklist
  * @param text
  *   Text of the task; 1-100 characters after entities parsing
  * @param parseMode
  *   Optional. Mode for parsing entities in the text. See formatting options for more details.
  * @param textEntities
  *   Optional. List of special entities that appear in the text, which can be specified instead of parse_mode.
  *   Currently, only bold, italic, underline, strikethrough, spoiler, and custom_emoji entities are allowed.
  */
final case class InputChecklistTask(
  id: Int,
  text: String,
  parseMode: Option[ParseMode] = Option.empty,
  textEntities: List[MessageEntity] = List.empty
)
