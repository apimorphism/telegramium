package telegramium.bots

/**
 * This object represents a sticker set.
 *
 * @param name Sticker set name
 * @param title Sticker set title
 * @param isAnimated True, if the sticker set contains animated stickers
 * @param containsMasks True, if the sticker set contains masks
 * @param stickers List of all set stickers
 * @param thumb Optional. Sticker set thumbnail in the .WEBP or .TGS format
 */
final case class StickerSet(name: String,
                            title: String,
                            isAnimated: Boolean,
                            containsMasks: Boolean,
                            stickers: List[Sticker] = List.empty,
                            thumb: Option[PhotoSize] = Option.empty
)
