package telegramium.bots

/** This object contains basic information about a refunded payment.
  *
  * @param currency
  *   Three-letter ISO 4217 currency code, or “XTR” for payments in Telegram Stars. Currently, always “XTR”
  * @param totalAmount
  *   Total refunded price in the smallest units of the currency (integer, not float/double). For example, for a price
  *   of US$
  * 1.45, total_amount = 145. See the exp parameter in currencies.json, it shows the number of digits past the decimal
  * point for each currency (2 for the majority of currencies).
  * @param invoicePayload
  *   Bot-specified invoice payload
  * @param telegramPaymentChargeId
  *   Telegram payment identifier
  * @param providerPaymentChargeId
  *   Optional. Provider payment identifier
  */
final case class RefundedPayment(
  currency: String,
  totalAmount: Long,
  invoicePayload: String,
  telegramPaymentChargeId: String,
  providerPaymentChargeId: Option[String] = Option.empty
)
