package telegramium.bots

/** Describes a checklist to create.
  *
  * @param title
  *   Title of the checklist; 1-255 characters after entities parsing
  * @param parseMode
  *   Optional. Mode for parsing entities in the title. See formatting options for more details.
  * @param titleEntities
  *   Optional. List of special entities that appear in the title, which can be specified instead of parse_mode.
  *   Currently, only bold, italic, underline, strikethrough, spoiler, and custom_emoji entities are allowed.
  * @param tasks
  *   List of 1-30 tasks in the checklist
  * @param othersCanAddTasks
  *   Optional. Pass True if other users can add tasks to the checklist
  * @param othersCanMarkTasksAsDone
  *   Optional. Pass True if other users can mark tasks as done or not done in the checklist
  */
final case class InputChecklist(
  title: String,
  parseMode: Option[ParseMode] = Option.empty,
  titleEntities: List[MessageEntity] = List.empty,
  tasks: List[InputChecklistTask] = List.empty,
  othersCanAddTasks: Option[Boolean] = Option.empty,
  othersCanMarkTasksAsDone: Option[Boolean] = Option.empty
)
