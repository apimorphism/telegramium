package telegramium.bots

/** This object represents a sticker set.*/
final case class StickerSet(
                            /** Sticker set name*/
                            name: String,
                            /** Sticker set title*/
                            title: String,
                            /** True, if the sticker set contains masks*/
                            containsMasks: Boolean,
                            /** List of all set stickers*/
                            stickers: List[Sticker] = List.empty)
