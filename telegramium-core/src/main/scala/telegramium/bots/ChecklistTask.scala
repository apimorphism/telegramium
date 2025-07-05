package telegramium.bots

/** Describes a task in a checklist.
  *
  * @param id
  *   Unique identifier of the task
  * @param text
  *   Text of the task
  * @param textEntities
  *   Optional. Special entities that appear in the task text
  * @param completedByUser
  *   Optional. User that completed the task; omitted if the task wasn't completed
  * @param completionDate
  *   Optional. Point in time (Unix timestamp) when the task was completed; 0 if the task wasn't completed
  */
final case class ChecklistTask(
  id: Int,
  text: String,
  textEntities: List[iozhik.OpenEnum[MessageEntity]] = List.empty,
  completedByUser: Option[User] = Option.empty,
  completionDate: Option[Int] = Option.empty
)
