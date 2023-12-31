package telegramium.bots

sealed trait ChatBoostSource {}

/** The boost was obtained by the creation of Telegram Premium gift codes to boost a chat. Each such code boosts the
  * chat 4 times for the duration of the corresponding Telegram Premium subscription.
  *
  * @param source
  *   Source of the boost, always “gift_code”
  * @param user
  *   User for which the gift code was created
  */
final case class ChatBoostSourceGiftCode(source: String, user: User) extends ChatBoostSource

/** The boost was obtained by the creation of a Telegram Premium giveaway. This boosts the chat 4 times for the duration
  * of the corresponding Telegram Premium subscription.
  *
  * @param source
  *   Source of the boost, always “giveaway”
  * @param giveawayMessageId
  *   Identifier of a message in the chat with the giveaway; the message could have been deleted already. May be 0 if
  *   the message isn't sent yet.
  * @param user
  *   Optional. User that won the prize in the giveaway if any
  * @param isUnclaimed
  *   Optional. True, if the giveaway was completed, but there was no user to win the prize
  */
final case class ChatBoostSourceGiveaway(
  source: String,
  giveawayMessageId: Int,
  user: Option[User] = Option.empty,
  isUnclaimed: Option[Boolean] = Option.empty
) extends ChatBoostSource

/** The boost was obtained by subscribing to Telegram Premium or by gifting a Telegram Premium subscription to another
  * user.
  *
  * @param source
  *   Source of the boost, always “premium”
  * @param user
  *   User that boosted the chat
  */
final case class ChatBoostSourcePremium(source: String, user: User) extends ChatBoostSource
