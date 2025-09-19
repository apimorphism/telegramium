package telegramium.bots

sealed trait RevenueWithdrawalState {}

/** The withdrawal is in progress. */
case object RevenueWithdrawalStatePending extends RevenueWithdrawalState

/** The withdrawal succeeded.
  *
  * @param date
  *   Date the withdrawal was completed in Unix time
  * @param url
  *   An HTTPS URL that can be used to see transaction details
  */
final case class RevenueWithdrawalStateSucceeded(date: Long, url: String) extends RevenueWithdrawalState

/** The withdrawal failed and the transaction was refunded. */
case object RevenueWithdrawalStateFailed extends RevenueWithdrawalState
