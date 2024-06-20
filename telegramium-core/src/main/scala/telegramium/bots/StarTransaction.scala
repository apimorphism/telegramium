package telegramium.bots

/** Describes a Telegram Star transaction.
  *
  * @param id
  *   Unique identifier of the transaction. Coincides with the identifer of the original transaction for refund
  *   transactions. Coincides with SuccessfulPayment.telegram_payment_charge_id for successful incoming payments from
  *   users.
  * @param amount
  *   Number of Telegram Stars transferred by the transaction
  * @param date
  *   Date the transaction was created in Unix time
  * @param source
  *   Optional. Source of an incoming transaction (e.g., a user purchasing goods or services, Fragment refunding a
  *   failed withdrawal). Only for incoming transactions
  * @param receiver
  *   Optional. Receiver of an outgoing transaction (e.g., a user for a purchase refund, Fragment for a withdrawal).
  *   Only for outgoing transactions
  */
final case class StarTransaction(
  id: String,
  amount: Int,
  date: Int,
  source: Option[iozhik.OpenEnum[TransactionPartner]] = Option.empty,
  receiver: Option[iozhik.OpenEnum[TransactionPartner]] = Option.empty
)
