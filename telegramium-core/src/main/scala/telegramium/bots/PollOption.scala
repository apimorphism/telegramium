package telegramium.bots

/** This object contains information about one answer option in a poll.
  *
  * @param text
  *   Option text, 1-100 characters
  * @param voterCount
  *   Number of users that voted for this option
  * @param textEntities
  *   Optional. Special entities that appear in the option text. Currently, only custom emoji entities are allowed in
  *   poll option texts
  */
final case class PollOption(text: String, voterCount: Int, textEntities: List[MessageEntity] = List.empty)
