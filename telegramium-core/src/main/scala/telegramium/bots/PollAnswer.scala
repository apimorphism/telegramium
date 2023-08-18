package telegramium.bots

/** This object represents an answer of a user in a non-anonymous poll.
  *
  * @param pollId
  *   Unique poll identifier
  * @param voterChat
  *   Optional. The chat that changed the answer to the poll, if the voter is anonymous
  * @param user
  *   Optional. The user that changed the answer to the poll, if the voter isn't anonymous
  * @param optionIds
  *   0-based identifiers of chosen answer options. May be empty if the vote was retracted.
  */
final case class PollAnswer(
  pollId: String,
  voterChat: Option[Chat] = Option.empty,
  user: Option[User] = Option.empty,
  optionIds: List[Int] = List.empty
)
