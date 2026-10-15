package telegramium.bots

/** Describes a checklist to create.
  *
  * @param title
  *   Title of the checklist; 1-255 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the title. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param titleEntities
  *   <em>Optional</em>. List of special entities that appear in the title, which can be specified instead of
  *   parse_mode. Currently, only <em>bold</em>, <em>italic</em>, <em>underline</em>, <em>strikethrough</em>,
  *   <em>spoiler</em>, <em>custom_emoji</em>, and <em>date_time</em> entities are allowed.
  * @param tasks
  *   List of 1-30 tasks in the checklist
  * @param othersCanAddTasks
  *   <em>Optional</em>. Pass <em>True</em> if other users can add tasks to the checklist
  * @param othersCanMarkTasksAsDone
  *   <em>Optional</em>. Pass <em>True</em> if other users can mark tasks as done or not done in the checklist
  */
final case class InputChecklist(
  title: String,
  parseMode: Option[ParseMode] = Option.empty,
  titleEntities: List[MessageEntity] = List.empty,
  tasks: List[InputChecklistTask] = List.empty,
  othersCanAddTasks: Option[Boolean] = Option.empty,
  othersCanMarkTasksAsDone: Option[Boolean] = Option.empty
)
