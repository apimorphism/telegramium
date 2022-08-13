package telegramium.bots

/** This object represents a sticker.
  *
  * @param fileId
  *   Identifier for this file, which can be used to download or reuse the file
  * @param fileUniqueId
  *   Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used
  *   to download or reuse the file.
  * @param type
  *   Type of the sticker, currently one of “regular”, “mask”, “custom_emoji”. The type of the sticker is independent
  *   from its format, which is determined by the fields is_animated and is_video.
  * @param width
  *   Sticker width
  * @param height
  *   Sticker height
  * @param isAnimated
  *   True, if the sticker is animated
  * @param isVideo
  *   True, if the sticker is a video sticker
  * @param thumb
  *   Optional. Sticker thumbnail in the .WEBP or .JPG format
  * @param emoji
  *   Optional. Emoji associated with the sticker
  * @param setName
  *   Optional. Name of the sticker set to which the sticker belongs
  * @param premiumAnimation
  *   Optional. For premium regular stickers, premium animation for the sticker
  * @param maskPosition
  *   Optional. For mask stickers, the position where the mask should be placed
  * @param customEmojiId
  *   Optional. For custom emoji stickers, unique identifier of the custom emoji
  * @param fileSize
  *   Optional. File size in bytes
  */
final case class Sticker(
  fileId: String,
  fileUniqueId: String,
  `type`: String,
  width: Int,
  height: Int,
  isAnimated: Boolean,
  isVideo: Boolean,
  thumb: Option[PhotoSize] = Option.empty,
  emoji: Option[Emoji] = Option.empty,
  setName: Option[String] = Option.empty,
  premiumAnimation: Option[File] = Option.empty,
  maskPosition: Option[MaskPosition] = Option.empty,
  customEmojiId: Option[String] = Option.empty,
  fileSize: Option[Long] = Option.empty
)
