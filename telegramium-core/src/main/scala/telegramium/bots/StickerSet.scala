package telegramium.bots

/** This object represents a sticker set.
  *
  * @param name
  *   Sticker set name
  * @param title
  *   Sticker set title
  * @param stickerType
  *   Type of stickers in the set, currently one of “regular”, “mask”, “custom_emoji”
  * @param stickers
  *   List of all set stickers
  * @param thumbnail
  *   Optional. Sticker set thumbnail in the .WEBP, .TGS, or .WEBM format
  */
final case class StickerSet(
  name: String,
  title: String,
  stickerType: String,
  stickers: List[Sticker] = List.empty,
  thumbnail: Option[PhotoSize] = Option.empty
)
