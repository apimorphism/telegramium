package telegramium.bots

/** This object contains information about a paid media purchase.
  *
  * @param from
  *   User who purchased the media
  * @param paidMediaPayload
  *   Bot-specified paid media payload
  */
final case class PaidMediaPurchased(from: User, paidMediaPayload: String)
