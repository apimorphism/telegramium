package telegramium.bots

/** Describes a service message about checklist tasks marked as done or not done.
  *
  * @param checklistMessage
  *   <em>Optional</em>. Message containing the checklist whose tasks were marked as done or not done. Note that the <a
  *   href="https://core.telegram.org/bots/api#message">Message</a> object in this field will not contain the
  *   <em>reply_to_message</em> field even if it itself is a reply.
  * @param markedAsDoneTaskIds
  *   <em>Optional</em>. Identifiers of the tasks that were marked as done
  * @param markedAsNotDoneTaskIds
  *   <em>Optional</em>. Identifiers of the tasks that were marked as not done
  */
final case class ChecklistTasksDone(
  checklistMessage: Option[Message] = Option.empty,
  markedAsDoneTaskIds: List[Int] = List.empty,
  markedAsNotDoneTaskIds: List[Int] = List.empty
)
