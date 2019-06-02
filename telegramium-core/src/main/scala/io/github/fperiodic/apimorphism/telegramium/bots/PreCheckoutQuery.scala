package io.github.fperiodic.apimorphism.telegramium.bots

/** This object contains information about an incoming pre-checkout query.*/
final case class PreCheckoutQuery(
                                  /** Unique query identifier*/
                                  id: String,
                                  /** User who sent the query*/
                                  from: User,
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
                                  orderInfo: Option[OrderInfo] = Option.empty)
