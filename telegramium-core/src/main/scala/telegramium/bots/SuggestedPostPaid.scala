package telegramium.bots

/** Describes a service message about a successful payment for a suggested post.
  *
  * @param currency
  *   Currency in which the payment was made. Currently, one of “XTR” for Telegram Stars or “TON” for toncoins
  * @param suggestedPostMessage
  *   Optional. Message containing the suggested post. Note that the Message object in this field will not contain the
  *   reply_to_message field even if it itself is a reply.
  * @param amount
  *   Optional. The amount of the currency that was received by the channel in nanotoncoins; for payments in toncoins
  *   only
  * @param starAmount
  *   Optional. The amount of Telegram Stars that was received by the channel; for payments in Telegram Stars only
  */
final case class SuggestedPostPaid(
  currency: String,
  suggestedPostMessage: Option[Message] = Option.empty,
  amount: Option[Long] = Option.empty,
  starAmount: Option[StarAmount] = Option.empty
)
