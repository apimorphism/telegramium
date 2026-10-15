package telegramium.bots

/** This object contains basic information about a refunded payment.
  *
  * @param currency
  *   Three-letter ISO 4217 <a href="https://core.telegram.org/bots/payments#supported-currencies">currency</a> code, or
  *   “XTR” for payments in <a href="https://t.me/BotNews/90">Telegram Stars</a>. Currently, always “XTR”.
  * @param totalAmount
  *   Total refunded price in the <em>smallest units</em> of the currency (integer, <strong>not</strong> float/double).
  *   For example, for a price of <code>US$ 1.45</code>, <code>total_amount = 145</code>. See the <em>exp</em> parameter
  *   in <a href="https://core.telegram.org/bots/payments/currencies.json">currencies.json</a>, it shows the number of
  *   digits past the decimal point for each currency (2 for the majority of currencies).
  * @param invoicePayload
  *   Bot-specified invoice payload
  * @param telegramPaymentChargeId
  *   Telegram payment identifier
  * @param providerPaymentChargeId
  *   <em>Optional</em>. Provider payment identifier
  */
final case class RefundedPayment(
  currency: String,
  totalAmount: Long,
  invoicePayload: String,
  telegramPaymentChargeId: String,
  providerPaymentChargeId: Option[String] = Option.empty
)
