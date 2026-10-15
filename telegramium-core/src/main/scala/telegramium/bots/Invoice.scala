package telegramium.bots

/** This object contains basic information about an invoice.
  *
  * @param title
  *   Product name
  * @param description
  *   Product description
  * @param startParameter
  *   Unique bot deep-linking parameter that can be used to generate this invoice
  * @param currency
  *   Three-letter ISO 4217 <a href="https://core.telegram.org/bots/payments#supported-currencies">currency</a> code, or
  *   “XTR” for payments in <a href="https://t.me/BotNews/90">Telegram Stars</a>
  * @param totalAmount
  *   Total price in the <em>smallest units</em> of the currency (integer, <strong>not</strong> float/double). For
  *   example, for a price of <code>US$ 1.45</code> pass <code>amount = 145</code>. See the <em>exp</em> parameter in <a
  *   href="https://core.telegram.org/bots/payments/currencies.json">currencies.json</a>, it shows the number of digits
  *   past the decimal point for each currency (2 for the majority of currencies).
  */
final case class Invoice(
  title: String,
  description: String,
  startParameter: String,
  currency: String,
  totalAmount: Long
)
