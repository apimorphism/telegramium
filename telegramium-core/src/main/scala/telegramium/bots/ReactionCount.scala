package telegramium.bots

/** Represents a reaction added to a message along with the number of times it was added.
  *
  * @param type
  *   Type of the reaction
  * @param totalCount
  *   Number of times the reaction was added
  */
final case class ReactionCount(`type`: ReactionType, totalCount: Int)
