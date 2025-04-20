package telegramium.bots

sealed trait TransactionPartner {}

/** Describes a transaction with a chat.
  *
  * @param chat
  *   Information about the chat
  * @param gift
  *   Optional. The gift sent to the chat by the bot
  */
final case class TransactionPartnerChat(chat: Chat, gift: Option[Gift] = Option.empty) extends TransactionPartner

/** Describes the affiliate program that issued the affiliate commission received via this transaction.
  *
  * @param commissionPerMille
  *   The number of Telegram Stars received by the bot for each 1000 Telegram Stars received by the affiliate program
  *   sponsor from referred users
  * @param sponsorUser
  *   Optional. Information about the bot that sponsored the affiliate program
  */
final case class TransactionPartnerAffiliateProgram(commissionPerMille: Int, sponsorUser: Option[User] = Option.empty)
    extends TransactionPartner

/** Describes a transaction with an unknown source or recipient. */
case object TransactionPartnerOther extends TransactionPartner

/** Describes a withdrawal transaction to the Telegram Ads platform. */
case object TransactionPartnerTelegramAds extends TransactionPartner

/** Describes a transaction with a user.
  *
  * @param transactionType
  *   Type of the transaction, currently one of “invoice_payment” for payments via invoices, “paid_media_payment” for
  *   payments for paid media, “gift_purchase” for gifts sent by the bot, “premium_purchase” for Telegram Premium
  *   subscriptions gifted by the bot, “business_account_transfer” for direct transfers from managed business accounts
  * @param user
  *   Information about the user
  * @param affiliate
  *   Optional. Information about the affiliate that received a commission via this transaction. Can be available only
  *   for “invoice_payment” and “paid_media_payment” transactions.
  * @param invoicePayload
  *   Optional. Bot-specified invoice payload. Can be available only for “invoice_payment” transactions.
  * @param subscriptionPeriod
  *   Optional. The duration of the paid subscription. Can be available only for “invoice_payment” transactions.
  * @param paidMedia
  *   Optional. Information about the paid media bought by the user; for “paid_media_payment” transactions only
  * @param paidMediaPayload
  *   Optional. Bot-specified paid media payload. Can be available only for “paid_media_payment” transactions.
  * @param gift
  *   Optional. The gift sent to the user by the bot; for “gift_purchase” transactions only
  * @param premiumSubscriptionDuration
  *   Optional. Number of months the gifted Telegram Premium subscription will be active for; for “premium_purchase”
  *   transactions only
  */
final case class TransactionPartnerUser(
  transactionType: String,
  user: User,
  affiliate: Option[AffiliateInfo] = Option.empty,
  invoicePayload: Option[String] = Option.empty,
  subscriptionPeriod: Option[Int] = Option.empty,
  paidMedia: List[iozhik.OpenEnum[PaidMedia]] = List.empty,
  paidMediaPayload: Option[String] = Option.empty,
  gift: Option[Gift] = Option.empty,
  premiumSubscriptionDuration: Option[Int] = Option.empty
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
