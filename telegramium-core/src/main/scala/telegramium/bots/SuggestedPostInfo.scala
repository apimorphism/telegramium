package telegramium.bots

/** Contains information about a suggested post.
  *
  * @param state
  *   State of the suggested post. Currently, it can be one of “pending”, “approved”, “declined”.
  * @param price
  *   Optional. Proposed price of the post. If the field is omitted, then the post is unpaid.
  * @param sendDate
  *   Optional. Proposed send date of the post. If the field is omitted, then the post can be published at any time
  *   within 30 days at the sole discretion of the user or administrator who approves it.
  */
final case class SuggestedPostInfo(
  state: String,
  price: Option[SuggestedPostPrice] = Option.empty,
  sendDate: Option[Long] = Option.empty
)
