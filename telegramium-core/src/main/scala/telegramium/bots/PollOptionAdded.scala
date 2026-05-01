package telegramium.bots

/** Describes a service message about an option added to a poll.
  *
  * @param optionPersistentId
  *   Unique identifier of the added option
  * @param optionText
  *   Option text
  * @param pollMessage
  *   Optional. Message containing the poll to which the option was added, if known. Note that the Message object in
  *   this field will not contain the reply_to_message field even if it itself is a reply.
  * @param optionTextEntities
  *   Optional. Special entities that appear in the option_text
  */
final case class PollOptionAdded(
  optionPersistentId: String,
  optionText: String,
  pollMessage: Option[MaybeInaccessibleMessage] = Option.empty,
  optionTextEntities: List[iozhik.OpenEnum[MessageEntity]] = List.empty
)
