package telegramium.bots

/** Describes a service message about tasks added to a checklist.
  *
  * @param checklistMessage
  *   Optional. Message containing the checklist to which the tasks were added. Note that the Message object in this
  *   field will not contain the reply_to_message field even if it itself is a reply.
  * @param tasks
  *   List of tasks added to the checklist
  */
final case class ChecklistTasksAdded(
  checklistMessage: Option[Message] = Option.empty,
  tasks: List[ChecklistTask] = List.empty
)
