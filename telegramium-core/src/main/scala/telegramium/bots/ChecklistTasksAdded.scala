package telegramium.bots

/** Describes a service message about tasks added to a checklist.
  *
  * @param checklistMessage
  *   <em>Optional</em>. Message containing the checklist to which the tasks were added. Note that the <a
  *   href="https://core.telegram.org/bots/api#message">Message</a> object in this field will not contain the
  *   <em>reply_to_message</em> field even if it itself is a reply.
  * @param tasks
  *   List of tasks added to the checklist
  */
final case class ChecklistTasksAdded(
  checklistMessage: Option[Message] = Option.empty,
  tasks: List[ChecklistTask] = List.empty
)
