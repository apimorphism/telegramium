package telegramium.bots

/** Contains information about the affiliate that received a commission via this transaction.
  *
  * @param commissionPerMille
  *   The number of Telegram Stars received by the affiliate for each 1000 Telegram Stars received by the bot from
  *   referred users
  * @param amount
  *   Integer amount of Telegram Stars received by the affiliate from the transaction, rounded to 0; can be negative for
  *   refunds
  * @param affiliateUser
  *   Optional. The bot or the user that received an affiliate commission if it was received by a bot or a user
  * @param affiliateChat
  *   Optional. The chat that received an affiliate commission if it was received by a chat
  * @param nanostarAmount
  *   Optional. The number of 1/1000000000 shares of Telegram Stars received by the affiliate; from -999999999 to
  *   999999999; can be negative for refunds
  */
final case class AffiliateInfo(
  commissionPerMille: Int,
  amount: Int,
  affiliateUser: Option[User] = Option.empty,
  affiliateChat: Option[Chat] = Option.empty,
  nanostarAmount: Option[Int] = Option.empty
)
