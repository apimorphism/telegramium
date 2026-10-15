package telegramium.bots

/** Describes a service message about the failed approval of a suggested post. Currently, only caused by insufficient
  * user funds at the time of approval.
  *
  * @param price
  *   Expected price of the post
  * @param suggestedPostMessage
  *   <em>Optional</em>. Message containing the suggested post whose approval has failed. Note that the <a
  *   href="https://core.telegram.org/bots/api#message">Message</a> object in this field will not contain the
  *   <em>reply_to_message</em> field even if it itself is a reply.
  */
final case class SuggestedPostApprovalFailed(
  price: SuggestedPostPrice,
  suggestedPostMessage: Option[Message] = Option.empty
)
