package telegramium.bots

/** Describes a service message about a successful payment for a suggested post.
  *
  * @param currency
  *   Currency in which the payment was made. Currently, one of “XTR” for Telegram Stars or “TON” for TON grams.
  * @param suggestedPostMessage
  *   <em>Optional</em>. Message containing the suggested post. Note that the <a
  *   href="https://core.telegram.org/bots/api#message">Message</a> object in this field will not contain the
  *   <em>reply_to_message</em> field even if it itself is a reply.
  * @param amount
  *   <em>Optional</em>. The amount of the currency that was received by the channel in nanograms; for payments in TON
  *   grams only
  * @param starAmount
  *   <em>Optional</em>. The amount of Telegram Stars that was received by the channel; for payments in Telegram Stars
  *   only
  */
final case class SuggestedPostPaid(
  currency: String,
  suggestedPostMessage: Option[Message] = Option.empty,
  amount: Option[Long] = Option.empty,
  starAmount: Option[StarAmount] = Option.empty
)
