package telegramium.bots

/** This object contains information about one answer option in a poll.
  *
  * @param text Option text, 1-100 characters
  * @param voterCount Number of users that voted for this option */
final case class PollOption(text: String, voterCount: Int)
