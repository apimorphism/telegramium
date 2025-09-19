package telegramium.bots

/** Describes a service message about a payment refund for a suggested post.
  *
  * @param reason
  *   Reason for the refund. Currently, one of “post_deleted” if the post was deleted within 24 hours of being posted or
  *   removed from scheduled messages without being posted, or “payment_refunded” if the payer refunded their payment.
  * @param suggestedPostMessage
  *   Optional. Message containing the suggested post. Note that the Message object in this field will not contain the
  *   reply_to_message field even if it itself is a reply.
  */
final case class SuggestedPostRefunded(reason: String, suggestedPostMessage: Option[Message] = Option.empty)
