package telegramium.bots

/** This object represents a sticker.*/
final case class Sticker(
                         /** Identifier for this file*/
                         fileId: String,
                         /** Sticker width*/
                         width: Int,
                         /** Sticker height*/
                         height: Int,
                         /** True, if the sticker is animated*/
                         isAnimated: Boolean,
                         /** Optional. Sticker thumbnail in the .webp or .jpg format*/
                         thumb: Option[PhotoSize] = Option.empty,
                         /** Optional. Emoji associated with the sticker*/
                         emoji: Option[String] = Option.empty,
                         /** Optional. Name of the sticker set to which the sticker
                           * belongs*/
                         setName: Option[String] = Option.empty,
                         /** Optional. For mask stickers, the position where the mask
                           * should be placed*/
                         maskPosition: Option[MaskPosition] = Option.empty,
                         /** Optional. File size*/
                         fileSize: Option[Int] = Option.empty)
