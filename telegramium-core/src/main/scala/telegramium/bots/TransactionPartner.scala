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
  * @param paidMedia
  *   Optional. Information about the paid media bought by the user
  */
final case class TransactionPartnerUser(
  user: User,
  invoicePayload: Option[String] = Option.empty,
  paidMedia: List[iozhik.OpenEnum[PaidMedia]] = List.empty
) extends TransactionPartner

/** Describes a withdrawal transaction with Fragment.
  *
  * @param withdrawalState
  *   Optional. State of the transaction if the transaction is outgoing
  */
final case class TransactionPartnerFragment(
  withdrawalState: Option[iozhik.OpenEnum[RevenueWithdrawalState]] = Option.empty
) extends TransactionPartner
