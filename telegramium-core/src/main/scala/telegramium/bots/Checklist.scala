package telegramium.bots

/** Describes a checklist.
  *
  * @param title
  *   Title of the checklist
  * @param titleEntities
  *   Optional. Special entities that appear in the checklist title
  * @param tasks
  *   List of tasks in the checklist
  * @param othersCanAddTasks
  *   Optional. True, if users other than the creator of the list can add tasks to the list
  * @param othersCanMarkTasksAsDone
  *   Optional. True, if users other than the creator of the list can mark tasks as done or not done
  */
final case class Checklist(
  title: String,
  titleEntities: List[iozhik.OpenEnum[MessageEntity]] = List.empty,
  tasks: List[ChecklistTask] = List.empty,
  othersCanAddTasks: Option[Boolean] = Option.empty,
  othersCanMarkTasksAsDone: Option[Boolean] = Option.empty
)
