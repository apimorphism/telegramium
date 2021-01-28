package telegramium.bots

/** This object represents a sticker.
  *
  * @param fileId Identifier for this file, which can be used to download or
  * reuse the file
  * @param fileUniqueId Unique identifier for this file, which is supposed to be
  * the same over time and for different bots. Can't be used to
  * download or reuse the file.
  * @param width Sticker width
  * @param height Sticker height
  * @param isAnimated True, if the sticker is animated
  * @param thumb Optional. Sticker thumbnail in the .WEBP or .JPG format
  * @param emoji Optional. Emoji associated with the sticker
  * @param setName Optional. Name of the sticker set to which the sticker
  * belongs
  * @param maskPosition Optional. For mask stickers, the position where the mask
  * should be placed
  * @param fileSize Optional. File size */
final case class Sticker(fileId: String,
                         fileUniqueId: String,
                         width: Int,
                         height: Int,
                         isAnimated: Boolean,
                         thumb: Option[PhotoSize] = Option.empty,
                         emoji: Option[Emoji] = Option.empty,
                         setName: Option[String] = Option.empty,
                         maskPosition: Option[MaskPosition] = Option.empty,
                         fileSize: Option[Int] = Option.empty)
