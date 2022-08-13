package telegramium.bots

/** This object represents a sticker set.
  *
  * @param name
  *   Sticker set name
  * @param title
  *   Sticker set title
  * @param stickerType
  *   Type of stickers in the set, currently one of “regular”, “mask”, “custom_emoji”
  * @param isAnimated
  *   True, if the sticker set contains animated stickers
  * @param isVideo
  *   True, if the sticker set contains video stickers
  * @param stickers
  *   List of all set stickers
  * @param thumb
  *   Optional. Sticker set thumbnail in the .WEBP, .TGS, or .WEBM format
  */
final case class StickerSet(
  name: String,
  title: String,
  stickerType: String,
  isAnimated: Boolean,
  isVideo: Boolean,
  stickers: List[Sticker] = List.empty,
  thumb: Option[PhotoSize] = Option.empty
)
