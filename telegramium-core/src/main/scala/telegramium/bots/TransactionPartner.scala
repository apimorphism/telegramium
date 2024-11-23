package telegramium.bots

sealed trait TransactionPartner {}

/** Describes a transaction with an unknown source or recipient. */
case object TransactionPartnerOther extends TransactionPartner

/** Describes a withdrawal transaction to the Telegram Ads platform. */
case object TransactionPartnerTelegramAds extends TransactionPartner

/** Describes a transaction with a user.
  *
  * @param user
  *   Information about the user
  * @param invoicePayload
  *   Optional. Bot-specified invoice payload
  * @param subscriptionPeriod
  *   Optional. The duration of the paid subscription
  * @param paidMedia
  *   Optional. Information about the paid media bought by the user
  * @param paidMediaPayload
  *   Optional. Bot-specified paid media payload
  * @param gift
  *   Optional. The gift sent to the user by the bot
  */
final case class TransactionPartnerUser(
  user: User,
  invoicePayload: Option[String] = Option.empty,
  subscriptionPeriod: Option[Int] = Option.empty,
  paidMedia: List[iozhik.OpenEnum[PaidMedia]] = List.empty,
  paidMediaPayload: Option[String] = Option.empty,
  gift: Option[Gift] = Option.empty
) extends TransactionPartner

/** Describes a transaction with payment for paid broadcasting.
  *
  * @param requestCount
  *   The number of successful requests that exceeded regular limits and were therefore billed
  */
final case class TransactionPartnerTelegramApi(requestCount: Int) extends TransactionPartner

/** Describes a withdrawal transaction with Fragment.
  *
  * @param withdrawalState
  *   Optional. State of the transaction if the transaction is outgoing
  */
final case class TransactionPartnerFragment(
  withdrawalState: Option[iozhik.OpenEnum[RevenueWithdrawalState]] = Option.empty
) extends TransactionPartner
