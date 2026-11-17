package telegramium.bots

/** Describes a task in a checklist.
  *
  * @param id
  *   Unique identifier of the task
  * @param text
  *   Text of the task
  * @param textEntities
  *   <em>Optional</em>. Special entities that appear in the task text
  * @param completedByUser
  *   <em>Optional</em>. User that completed the task; omitted if the task wasn't completed by a user
  * @param completedByChat
  *   <em>Optional</em>. Chat that completed the task; omitted if the task wasn't completed by a chat
  * @param completionDate
  *   <em>Optional</em>. Point in time (Unix timestamp) when the task was completed; 0 if the task wasn't completed
  */
final case class ChecklistTask(
  id: Int,
  text: String,
  textEntities: List[iozhik.OpenEnum[MessageEntity]] = List.empty,
  completedByUser: Option[User] = Option.empty,
  completedByChat: Option[Chat] = Option.empty,
  completionDate: Option[Long] = Option.empty
)
