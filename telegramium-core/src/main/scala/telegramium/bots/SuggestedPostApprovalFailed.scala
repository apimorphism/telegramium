package telegramium.bots

/** Describes a service message about the failed approval of a suggested post. Currently, only caused by insufficient
  * user funds at the time of approval.
  *
  * @param price
  *   Expected price of the post
  * @param suggestedPostMessage
  *   Optional. Message containing the suggested post whose approval has failed. Note that the Message object in this
  *   field will not contain the reply_to_message field even if it itself is a reply.
  */
final case class SuggestedPostApprovalFailed(
  price: SuggestedPostPrice,
  suggestedPostMessage: Option[Message] = Option.empty
)
