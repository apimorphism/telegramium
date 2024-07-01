package telegramium.bots

/** Describes the paid media added to a message.
  *
  * @param starCount
  *   The number of Telegram Stars that must be paid to buy access to the media
  * @param paidMedia
  *   Information about the paid media
  */
final case class PaidMediaInfo(starCount: Int, paidMedia: List[iozhik.OpenEnum[PaidMedia]] = List.empty)
