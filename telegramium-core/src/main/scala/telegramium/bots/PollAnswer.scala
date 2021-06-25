package telegramium.bots

/**
 * This object represents an answer of a user in a non-anonymous poll.
 *
 * @param pollId Unique poll identifier
 * @param user The user, who changed the answer to the poll
 * @param optionIds 0-based identifiers of answer options, chosen by the user.
 * May be empty if the user retracted their vote.
 */
final case class PollAnswer(pollId: String, user: User, optionIds: List[Int] = List.empty)
