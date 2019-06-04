package telegramium.bots

/** This object contains basic information about a successful payment.*/
final case class SuccessfulPayment(
                                   /** Three-letter ISO 4217 currency code*/
                                   currency: String,
                                   /** Total price in the smallest units of the currency (integer,
                                     * not float/double). For example, for a price of US$ 1.45 pass
                                     * amount = 145. See the exp parameter in currencies.json, it
                                     * shows the number of digits past the decimal point for each
                                     * currency (2 for the majority of currencies).*/
                                   totalAmount: Int,
                                   /** Bot specified invoice payload*/
                                   invoicePayload: String,
                                   /** Optional. Identifier of the shipping option chosen by the
                                     * user*/
                                   shippingOptionId: Option[String] = Option.empty,
                                   /** Optional. Order info provided by the user*/
                                   orderInfo: Option[OrderInfo] = Option.empty,
                                   /** Telegram payment identifier*/
                                   telegramPaymentChargeId: String,
                                   /** Provider payment identifier*/
                                   providerPaymentChargeId: String)
