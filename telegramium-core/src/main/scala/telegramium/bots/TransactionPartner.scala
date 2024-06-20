package telegramium.bots

sealed trait TransactionPartner {}

/** Describes a transaction with an unknown source or recipient. */
case object TransactionPartnerOther extends TransactionPartner

/** Describes a transaction with a user.
  *
  * @param user
  *   Information about the user
  */
final case class TransactionPartnerUser(user: User) extends TransactionPartner

/** Describes a withdrawal transaction with Fragment.
  *
  * @param withdrawalState
  *   Optional. State of the transaction if the transaction is outgoing
  */
final case class TransactionPartnerFragment(
  withdrawalState: Option[iozhik.OpenEnum[RevenueWithdrawalState]] = Option.empty
) extends TransactionPartner
