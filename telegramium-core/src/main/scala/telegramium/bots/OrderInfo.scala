package telegramium.bots

/** This object represents information about an order.
  *
  * @param name Optional. User name
  * @param phoneNumber Optional. User's phone number
  * @param email Optional. User email
  * @param shippingAddress Optional. User shipping address */
final case class OrderInfo(name: Option[String] = Option.empty,
                           phoneNumber: Option[String] = Option.empty,
                           email: Option[String] = Option.empty,
                           shippingAddress: Option[ShippingAddress] = Option.empty)
