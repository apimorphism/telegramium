package telegramium.bots

/** This object describes a unique gift that was upgraded from a regular gift.
  *
  * @param baseName
  *   Human-readable name of the regular gift from which this unique gift was upgraded
  * @param name
  *   Unique name of the gift. This name can be used in https://t.me/nft/... links and story areas
  * @param number
  *   Unique number of the upgraded gift among gifts upgraded from the same regular gift
  * @param model
  *   Model of the gift
  * @param symbol
  *   Symbol of the gift
  * @param backdrop
  *   Backdrop of the gift
  * @param publisherChat
  *   Optional. Information about the chat that published the gift
  */
final case class UniqueGift(
  baseName: String,
  name: String,
  number: Int,
  model: UniqueGiftModel,
  symbol: UniqueGiftSymbol,
  backdrop: UniqueGiftBackdrop,
  publisherChat: Option[Chat] = Option.empty
)
