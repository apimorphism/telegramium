package telegramium.bots

/** Contains the list of gifts received and owned by a user or a chat.
  *
  * @param totalCount
  *   The total number of gifts owned by the user or the chat
  * @param gifts
  *   The list of gifts
  * @param nextOffset
  *   Optional. Offset for the next request. If empty, then there are no more results
  */
final case class OwnedGifts(
  totalCount: Int,
  gifts: List[iozhik.OpenEnum[OwnedGift]] = List.empty,
  nextOffset: Option[String] = Option.empty
)
