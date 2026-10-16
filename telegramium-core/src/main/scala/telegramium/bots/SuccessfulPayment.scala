package telegramium.bots

/** This object contains basic information about a successful payment. Note that if the buyer initiates a chargeback
  * with the relevant payment provider following this transaction, the funds may be debited from your balance. This is
  * outside of Telegram's control.
  *
  * @param currency
  *   Three-letter ISO 4217 <a href="https://core.telegram.org/bots/payments#supported-currencies">currency</a> code, or
  *   “XTR” for payments in <a href="https://t.me/BotNews/90">Telegram Stars</a>
  * @param totalAmount
  *   Total price in the <em>smallest units</em> of the currency (integer, <strong>not</strong> float/double). For
  *   example, for a price of <code>US$ 1.45</code> pass <code>amount = 145</code>. See the <em>exp</em> parameter in <a
  *   href="https://core.telegram.org/bots/payments/currencies.json">currencies.json</a>, it shows the number of digits
  *   past the decimal point for each currency (2 for the majority of currencies).
  * @param invoicePayload
  *   Bot-specified invoice payload
  * @param telegramPaymentChargeId
  *   Telegram payment identifier
  * @param providerPaymentChargeId
  *   Provider payment identifier
  * @param subscriptionExpirationDate
  *   <em>Optional</em>. Expiration date of the subscription, in Unix time; for recurring payments only
  * @param isRecurring
  *   <em>Optional</em>. <em>True</em>, if the payment is a recurring payment for a subscription
  * @param isFirstRecurring
  *   <em>Optional</em>. <em>True</em>, if the payment is the first payment for a subscription
  * @param shippingOptionId
  *   <em>Optional</em>. Identifier of the shipping option chosen by the user
  * @param orderInfo
  *   <em>Optional</em>. Order information provided by the user
  */
final case class SuccessfulPayment(
  currency: String,
  totalAmount: Long,
  invoicePayload: String,
  telegramPaymentChargeId: String,
  providerPaymentChargeId: String,
  subscriptionExpirationDate: Option[Long] = Option.empty,
  isRecurring: Option[Boolean] = Option.empty,
  isFirstRecurring: Option[Boolean] = Option.empty,
  shippingOptionId: Option[String] = Option.empty,
  orderInfo: Option[OrderInfo] = Option.empty
)
