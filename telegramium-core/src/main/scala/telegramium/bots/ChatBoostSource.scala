package telegramium.bots

sealed trait ChatBoostSource {}

/** The boost was obtained by the creation of Telegram Premium gift codes to boost a chat. Each such code boosts the
  * chat 4 times for the duration of the corresponding Telegram Premium subscription.
  *
  * @param user
  *   User for which the gift code was created
  */
final case class ChatBoostSourceGiftCode(user: User) extends ChatBoostSource

/** The boost was obtained by the creation of a Telegram Premium or a Telegram Star giveaway. This boosts the chat 4
  * times for the duration of the corresponding Telegram Premium subscription for Telegram Premium giveaways and
  * prize_star_count / 500 times for one year for Telegram Star giveaways.
  *
  * @param giveawayMessageId
  *   Identifier of a message in the chat with the giveaway; the message could have been deleted already. May be 0 if
  *   the message isn't sent yet.
  * @param user
  *   Optional. User that won the prize in the giveaway if any; for Telegram Premium giveaways only
  * @param prizeStarCount
  *   Optional. The number of Telegram Stars to be split between giveaway winners; for Telegram Star giveaways only
  * @param isUnclaimed
  *   Optional. True, if the giveaway was completed, but there was no user to win the prize
  */
final case class ChatBoostSourceGiveaway(
  giveawayMessageId: Int,
  user: Option[User] = Option.empty,
  prizeStarCount: Option[Int] = Option.empty,
  isUnclaimed: Option[Boolean] = Option.empty
) extends ChatBoostSource

/** The boost was obtained by subscribing to Telegram Premium or by gifting a Telegram Premium subscription to another
  * user.
  *
  * @param user
  *   User that boosted the chat
  */
final case class ChatBoostSourcePremium(user: User) extends ChatBoostSource
