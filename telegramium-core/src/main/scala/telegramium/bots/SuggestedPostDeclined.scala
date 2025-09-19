package telegramium.bots

/** Describes a service message about the rejection of a suggested post.
  *
  * @param suggestedPostMessage
  *   Optional. Message containing the suggested post. Note that the Message object in this field will not contain the
  *   reply_to_message field even if it itself is a reply.
  * @param comment
  *   Optional. Comment with which the post was declined
  */
final case class SuggestedPostDeclined(
  suggestedPostMessage: Option[Message] = Option.empty,
  comment: Option[String] = Option.empty
)
