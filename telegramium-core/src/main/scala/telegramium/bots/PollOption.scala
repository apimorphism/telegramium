package telegramium.bots

/** This object contains information about one answer option in a poll.*/
final case class PollOption(
                            /** Option text, 1-100 characters*/
                            text: String,
                            /** Number of users that voted for this option*/
                            voterCount: Int)
