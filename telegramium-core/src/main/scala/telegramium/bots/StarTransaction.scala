package telegramium.bots

/** Describes a Telegram Star transaction. Note that if the buyer initiates a chargeback with the payment provider from
  * whom they acquired Stars (e.g., Apple, Google) following this transaction, the refunded Stars will be deducted from
  * the bot's balance. This is outside of Telegram's control.
  *
  * @param id
  *   Unique identifier of the transaction. Coincides with the identifier of the original transaction for refund
  *   transactions. Coincides with SuccessfulPayment.telegram_payment_charge_id for successful incoming payments from
  *   users.
  * @param amount
  *   Integer amount of Telegram Stars transferred by the transaction
  * @param date
  *   Date the transaction was created in Unix time
  * @param nanostarAmount
  *   Optional. The number of 1/1000000000 shares of Telegram Stars transferred by the transaction; from 0 to 999999999
  * @param source
  *   Optional. Source of an incoming transaction (e.g., a user purchasing goods or services, Fragment refunding a
  *   failed withdrawal). Only for incoming transactions
  * @param receiver
  *   Optional. Receiver of an outgoing transaction (e.g., a user for a purchase refund, Fragment for a withdrawal).
  *   Only for outgoing transactions
  */
final case class StarTransaction(
  id: String,
  amount: Long,
  date: Long,
  nanostarAmount: Option[Int] = Option.empty,
  source: Option[iozhik.OpenEnum[TransactionPartner]] = Option.empty,
  receiver: Option[iozhik.OpenEnum[TransactionPartner]] = Option.empty
)
