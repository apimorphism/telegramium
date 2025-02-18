package telegramium.bots

/** This object contains basic information about a successful payment. Note that if the buyer initiates a chargeback
  * with the relevant payment provider following this transaction, the funds may be debited from your balance. This is
  * outside of Telegram's control.
  *
  * @param currency
  *   Three-letter ISO 4217 currency code, or “XTR” for payments in Telegram Stars
  * @param totalAmount
  *   Total price in the smallest units of the currency (integer, not float/double). For example, for a price of US$
  *   1.45 pass amount = 145. See the exp parameter in currencies.json, it shows the number of digits past the decimal
  *   point for each currency (2 for the majority of currencies).
  * @param invoicePayload
  *   Bot-specified invoice payload
  * @param telegramPaymentChargeId
  *   Telegram payment identifier
  * @param providerPaymentChargeId
  *   Provider payment identifier
  * @param subscriptionExpirationDate
  *   Optional. Expiration date of the subscription, in Unix time; for recurring payments only
  * @param isRecurring
  *   Optional. True, if the payment is a recurring payment for a subscription
  * @param isFirstRecurring
  *   Optional. True, if the payment is the first payment for a subscription
  * @param shippingOptionId
  *   Optional. Identifier of the shipping option chosen by the user
  * @param orderInfo
  *   Optional. Order information provided by the user
  */
final case class SuccessfulPayment(
  currency: String,
  totalAmount: Int,
  invoicePayload: String,
  telegramPaymentChargeId: String,
  providerPaymentChargeId: String,
  subscriptionExpirationDate: Option[Int] = Option.empty,
  isRecurring: Option[Boolean] = Option.empty,
  isFirstRecurring: Option[Boolean] = Option.empty,
  shippingOptionId: Option[String] = Option.empty,
  orderInfo: Option[OrderInfo] = Option.empty
)
