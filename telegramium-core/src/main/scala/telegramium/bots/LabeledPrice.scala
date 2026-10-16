package telegramium.bots

/** This object represents a portion of the price for goods or services.
  *
  * @param label
  *   Portion label
  * @param amount
  *   Price of the product in the <em>smallest units</em> of the <a
  *   href="https://core.telegram.org/bots/payments#supported-currencies">currency</a> (integer, <strong>not</strong>
  *   float/double). For example, for a price of <code>US$ 1.45</code> pass <code>amount = 145</code>. See the
  *   <em>exp</em> parameter in <a href="https://core.telegram.org/bots/payments/currencies.json">currencies.json</a>,
  *   it shows the number of digits past the decimal point for each currency (2 for the majority of currencies).
  */
final case class LabeledPrice(label: String, amount: Long)
