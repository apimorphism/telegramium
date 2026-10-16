package telegramium.bots

/** Describes a service message about an option deleted from a poll.
  *
  * @param optionPersistentId
  *   Unique identifier of the deleted option
  * @param optionText
  *   Option text
  * @param pollMessage
  *   <em>Optional</em>. Message containing the poll from which the option was deleted, if known. Note that the <a
  *   href="https://core.telegram.org/bots/api#message">Message</a> object in this field will not contain the
  *   <em>reply_to_message</em> field even if it itself is a reply.
  * @param optionTextEntities
  *   <em>Optional</em>. Special entities that appear in the <em>option_text</em>
  */
final case class PollOptionDeleted(
  optionPersistentId: String,
  optionText: String,
  pollMessage: Option[MaybeInaccessibleMessage] = Option.empty,
  optionTextEntities: List[iozhik.OpenEnum[MessageEntity]] = List.empty
)
