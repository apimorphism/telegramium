package telegramium.bots

/** This object represents a message about the completion of a giveaway with public winners.
  *
  * @param chat
  *   The chat that created the giveaway
  * @param giveawayMessageId
  *   Identifier of the messsage with the giveaway in the chat
  * @param winnersSelectionDate
  *   Point in time (Unix timestamp) when winners of the giveaway were selected
  * @param winnerCount
  *   Total number of winners in the giveaway
  * @param winners
  *   List of up to 100 winners of the giveaway
  * @param additionalChatCount
  *   Optional. The number of other chats the user had to join in order to be eligible for the giveaway
  * @param premiumSubscriptionMonthCount
  *   Optional. The number of months the Telegram Premium subscription won from the giveaway will be active for
  * @param unclaimedPrizeCount
  *   Optional. Number of undistributed prizes
  * @param onlyNewMembers
  *   Optional. True, if only users who had joined the chats after the giveaway started were eligible to win
  * @param wasRefunded
  *   Optional. True, if the giveaway was canceled because the payment for it was refunded
  * @param prizeDescription
  *   Optional. Description of additional giveaway prize
  */
final case class GiveawayWinners(
  chat: Chat,
  giveawayMessageId: Int,
  winnersSelectionDate: Int,
  winnerCount: Int,
  winners: List[User] = List.empty,
  additionalChatCount: Option[Int] = Option.empty,
  premiumSubscriptionMonthCount: Option[Int] = Option.empty,
  unclaimedPrizeCount: Option[Int] = Option.empty,
  onlyNewMembers: Option[Boolean] = Option.empty,
  wasRefunded: Option[Boolean] = Option.empty,
  prizeDescription: Option[String] = Option.empty
)
