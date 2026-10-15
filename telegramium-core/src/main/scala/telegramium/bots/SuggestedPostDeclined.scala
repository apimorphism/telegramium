package telegramium.bots

/** Describes a service message about the rejection of a suggested post.
  *
  * @param suggestedPostMessage
  *   <em>Optional</em>. Message containing the suggested post. Note that the <a
  *   href="https://core.telegram.org/bots/api#message">Message</a> object in this field will not contain the
  *   <em>reply_to_message</em> field even if it itself is a reply.
  * @param comment
  *   <em>Optional</em>. Comment with which the post was declined
  */
final case class SuggestedPostDeclined(
  suggestedPostMessage: Option[Message] = Option.empty,
  comment: Option[String] = Option.empty
)
