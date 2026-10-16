package telegramium.bots

/** This object represents information about an order.
  *
  * @param name
  *   <em>Optional</em>. User name
  * @param phoneNumber
  *   <em>Optional</em>. User's phone number
  * @param email
  *   <em>Optional</em>. User email
  * @param shippingAddress
  *   <em>Optional</em>. User shipping address
  */
final case class OrderInfo(
  name: Option[String] = Option.empty,
  phoneNumber: Option[String] = Option.empty,
  email: Option[String] = Option.empty,
  shippingAddress: Option[ShippingAddress] = Option.empty
)
