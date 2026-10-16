package telegramium.bots.client

import telegramium.bots.LabeledPrice

/** @param title
  *   Product name, 1-32 characters
  * @param description
  *   Product description, 1-255 characters
  * @param payload
  *   Bot-defined invoice payload, 1-128 bytes. This will not be displayed to the user, use it for your internal
  *   processes.
  * @param currency
  *   Three-letter ISO 4217 currency code, see <a
  *   href="https://core.telegram.org/bots/payments#supported-currencies">more on currencies</a>. Pass “XTR” for
  *   payments in <a href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the link will be created. For payments in <a
  *   href="https://t.me/BotNews/90">Telegram Stars</a> only.
  * @param providerToken
  *   Payment provider token, obtained via <a href="https://t.me/botfather">&#064;BotFather</a>. Pass an empty string
  *   for payments in <a href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param prices
  *   Price breakdown, a JSON-serialized list of components (e.g. product price, tax, discount, delivery cost, delivery
  *   tax, bonus, etc.). Must contain exactly one item for payments in <a href="https://t.me/BotNews/90">Telegram
  *   Stars</a>.
  * @param subscriptionPeriod
  *   The number of seconds the subscription will be active for before the next payment. The currency must be set to
  *   “XTR” (Telegram Stars) if the parameter is used. Currently, it must always be 2592000 (30 days) if specified. Any
  *   number of subscriptions can be active for a given bot at the same time, including multiple concurrent
  *   subscriptions from the same user. Subscription price must no exceed 10000 Telegram Stars.
  * @param maxTipAmount
  *   The maximum accepted amount for tips in the <em>smallest units</em> of the currency (integer, <strong>not</strong>
  *   float/double). For example, for a maximum tip of <code>US$
  * 1.45</code> pass <code>max_tip_amount = 145</code>. See the <em>exp</em> parameter in <a
  * href="https://core.telegram.org/bots/payments/currencies.json">currencies.json</a>, it shows the number of digits
  * past the decimal point for each currency (2 for the majority of currencies). Defaults to 0. Not supported for
  * payments in <a href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param suggestedTipAmounts
  *   A JSON-serialized Array of suggested amounts of tips in the <em>smallest units</em> of the currency (integer,
  *   <strong>not</strong> float/double). At most 4 suggested tip amounts can be specified. The suggested tip amounts
  *   must be positive, passed in a strictly increased order and must not exceed <em>max_tip_amount</em>.
  * @param providerData
  *   JSON-serialized data about the invoice, which will be shared with the payment provider. A detailed description of
  *   required fields should be provided by the payment provider.
  * @param photoUrl
  *   URL of the product photo for the invoice. Can be a photo of the goods or a marketing image for a service.
  * @param photoSize
  *   Photo size in bytes
  * @param photoWidth
  *   Photo width
  * @param photoHeight
  *   Photo height
  * @param needName
  *   Pass <em>True</em> if you require the user's full name to complete the order. Ignored for payments in <a
  *   href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param needPhoneNumber
  *   Pass <em>True</em> if you require the user's phone number to complete the order. Ignored for payments in <a
  *   href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param needEmail
  *   Pass <em>True</em> if you require the user's email address to complete the order. Ignored for payments in <a
  *   href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param needShippingAddress
  *   Pass <em>True</em> if you require the user's shipping address to complete the order. Ignored for payments in <a
  *   href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param sendPhoneNumberToProvider
  *   Pass <em>True</em> if the user's phone number should be sent to the provider. Ignored for payments in <a
  *   href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param sendEmailToProvider
  *   Pass <em>True</em> if the user's email address should be sent to the provider. Ignored for payments in <a
  *   href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param isFlexible
  *   Pass <em>True</em> if the final price depends on the shipping method. Ignored for payments in <a
  *   href="https://t.me/BotNews/90">Telegram Stars</a>.
  */
final case class CreateInvoiceLinkReq(
  title: String,
  description: String,
  payload: String,
  currency: String,
  businessConnectionId: Option[String] = Option.empty,
  providerToken: Option[String] = Option.empty,
  prices: List[LabeledPrice] = List.empty,
  subscriptionPeriod: Option[Int] = Option.empty,
  maxTipAmount: Option[Long] = Option.empty,
  suggestedTipAmounts: List[Long] = List.empty,
  providerData: Option[String] = Option.empty,
  photoUrl: Option[String] = Option.empty,
  photoSize: Option[Long] = Option.empty,
  photoWidth: Option[Int] = Option.empty,
  photoHeight: Option[Int] = Option.empty,
  needName: Option[Boolean] = Option.empty,
  needPhoneNumber: Option[Boolean] = Option.empty,
  needEmail: Option[Boolean] = Option.empty,
  needShippingAddress: Option[Boolean] = Option.empty,
  sendPhoneNumberToProvider: Option[Boolean] = Option.empty,
  sendEmailToProvider: Option[Boolean] = Option.empty,
  isFlexible: Option[Boolean] = Option.empty
)
