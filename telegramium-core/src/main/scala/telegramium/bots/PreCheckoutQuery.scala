package telegramium.bots

/** This object contains information about an incoming pre-checkout query.
  *
  * @param id
  *   Unique query identifier
  * @param from
  *   User who sent the query
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
  * @param shippingOptionId
  *   <em>Optional</em>. Identifier of the shipping option chosen by the user
  * @param orderInfo
  *   <em>Optional</em>. Order information provided by the user
  */
final case class PreCheckoutQuery(
  id: String,
  from: User,
  currency: String,
  totalAmount: Long,
  invoicePayload: String,
  shippingOptionId: Option[String] = Option.empty,
  orderInfo: Option[OrderInfo] = Option.empty
)
