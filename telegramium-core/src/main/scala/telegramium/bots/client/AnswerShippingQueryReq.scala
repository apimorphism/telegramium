package telegramium.bots.client

import telegramium.bots.ShippingOption

/** @param shippingQueryId
  *   Unique identifier for the query to be answered
  * @param ok
  *   Pass <em>True</em> if delivery to the specified address is possible and <em>False</em> if there are any problems
  *   (for example, if delivery to the specified address is not possible)
  * @param shippingOptions
  *   Required if <em>ok</em> is <em>True</em>. A JSON-serialized Array of available shipping options.
  * @param errorMessage
  *   Required if <em>ok</em> is <em>False</em>. Error message in human readable form that explains why it is impossible
  *   to complete the order (e.g. “Sorry, delivery to your desired address is unavailable”). Telegram will display this
  *   message to the user.
  */
final case class AnswerShippingQueryReq(
  shippingQueryId: String,
  ok: Boolean,
  shippingOptions: List[ShippingOption] = List.empty,
  errorMessage: Option[String] = Option.empty
)
