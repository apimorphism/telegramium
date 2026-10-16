package telegramium.bots

/** Describes a service message about the approval of a suggested post.
  *
  * @param sendDate
  *   Date when the post will be published
  * @param suggestedPostMessage
  *   <em>Optional</em>. Message containing the suggested post. Note that the <a
  *   href="https://core.telegram.org/bots/api#message">Message</a> object in this field will not contain the
  *   <em>reply_to_message</em> field even if it itself is a reply.
  * @param price
  *   <em>Optional</em>. Amount paid for the post
  */
final case class SuggestedPostApproved(
  sendDate: Long,
  suggestedPostMessage: Option[Message] = Option.empty,
  price: Option[SuggestedPostPrice] = Option.empty
)
