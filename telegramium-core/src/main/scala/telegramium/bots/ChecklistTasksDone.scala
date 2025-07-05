package telegramium.bots

/** Describes a service message about checklist tasks marked as done or not done.
  *
  * @param checklistMessage
  *   Optional. Message containing the checklist whose tasks were marked as done or not done. Note that the Message
  *   object in this field will not contain the reply_to_message field even if it itself is a reply.
  * @param markedAsDoneTaskIds
  *   Optional. Identifiers of the tasks that were marked as done
  * @param markedAsNotDoneTaskIds
  *   Optional. Identifiers of the tasks that were marked as not done
  */
final case class ChecklistTasksDone(
  checklistMessage: Option[Message] = Option.empty,
  markedAsDoneTaskIds: List[Int] = List.empty,
  markedAsNotDoneTaskIds: List[Int] = List.empty
)
