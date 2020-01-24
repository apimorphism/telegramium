package telegramium.bots

/** This object represents an answer of a user in a non-anonymous poll.*/
final case class PollAnswer(
                            /** Unique poll identifier*/
                            pollId: String,
                            /** The user, who changed the answer to the poll*/
                            user: User,
                            /** 0-based identifiers of answer options, chosen by the user.
                              * May be empty if the user retracted their vote.*/
                            optionIds: List[Int] = List.empty)
