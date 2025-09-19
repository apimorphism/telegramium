package telegramium.bots

/** Describes a service message about the approval of a suggested post.
  *
  * @param sendDate
  *   Date when the post will be published
  * @param suggestedPostMessage
  *   Optional. Message containing the suggested post. Note that the Message object in this field will not contain the
  *   reply_to_message field even if it itself is a reply.
  * @param price
  *   Optional. Amount paid for the post
  */
final case class SuggestedPostApproved(
  sendDate: Long,
  suggestedPostMessage: Option[Message] = Option.empty,
  price: Option[SuggestedPostPrice] = Option.empty
)
