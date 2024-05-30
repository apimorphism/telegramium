package telegramium.bots

/** This object contains information about an incoming pre-checkout query.
  *
  * @param id
  *   Unique query identifier
  * @param from
  *   User who sent the query
  * @param currency
  *   Three-letter ISO 4217 currency code, or “XTR” for payments in Telegram Stars
  * @param totalAmount
  *   Total price in the smallest units of the currency (integer, not float/double). For example, for a price of US$
  *   1.45 pass amount = 145. See the exp parameter in currencies.json, it shows the number of digits past the decimal
  *   point for each currency (2 for the majority of currencies).
  * @param invoicePayload
  *   Bot specified invoice payload
  * @param shippingOptionId
  *   Optional. Identifier of the shipping option chosen by the user
  * @param orderInfo
  *   Optional. Order information provided by the user
  */
final case class PreCheckoutQuery(
  id: String,
  from: User,
  currency: String,
  totalAmount: Int,
  invoicePayload: String,
  shippingOptionId: Option[String] = Option.empty,
  orderInfo: Option[OrderInfo] = Option.empty
)
