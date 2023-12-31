package telegramium.bots

/** This object represents a service message about the completion of a giveaway without public winners.
  *
  * @param winnerCount
  *   Number of winners in the giveaway
  * @param unclaimedPrizeCount
  *   Optional. Number of undistributed prizes
  * @param giveawayMessage
  *   Optional. Message with the giveaway that was completed, if it wasn't deleted
  */
final case class GiveawayCompleted(
  winnerCount: Int,
  unclaimedPrizeCount: Option[Int] = Option.empty,
  giveawayMessage: Option[Message] = Option.empty
)
