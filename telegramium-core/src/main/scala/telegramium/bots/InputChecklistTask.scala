package telegramium.bots

/** Describes a task to add to a checklist.
  *
  * @param id
  *   Unique identifier of the task; must be positive and unique among all task identifiers currently present in the
  *   checklist
  * @param text
  *   Text of the task; 1-100 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the text. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param textEntities
  *   <em>Optional</em>. List of special entities that appear in the text, which can be specified instead of parse_mode.
  *   Currently, only <em>bold</em>, <em>italic</em>, <em>underline</em>, <em>strikethrough</em>, <em>spoiler</em>,
  *   <em>custom_emoji</em>, and <em>date_time</em> entities are allowed.
  */
final case class InputChecklistTask(
  id: Int,
  text: String,
  parseMode: Option[ParseMode] = Option.empty,
  textEntities: List[MessageEntity] = List.empty
)
