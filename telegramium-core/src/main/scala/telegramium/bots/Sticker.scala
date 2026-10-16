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
  *   from its format, which is determined by the fields <em>is_animated</em> and <em>is_video</em>.
  * @param width
  *   Sticker width
  * @param height
  *   Sticker height
  * @param isAnimated
  *   <em>True</em>, if the sticker is <a href="https://telegram.org/blog/animated-stickers">animated</a>
  * @param isVideo
  *   <em>True</em>, if the sticker is a <a href="https://telegram.org/blog/video-stickers-better-reactions">video
  *   sticker</a>
  * @param thumbnail
  *   <em>Optional</em>. Sticker thumbnail in the .WEBP or .JPG format
  * @param emoji
  *   <em>Optional</em>. Emoji associated with the sticker
  * @param setName
  *   <em>Optional</em>. Name of the sticker set to which the sticker belongs
  * @param premiumAnimation
  *   <em>Optional</em>. For premium regular stickers, premium animation for the sticker
  * @param maskPosition
  *   <em>Optional</em>. For mask stickers, the position where the mask should be placed
  * @param customEmojiId
  *   <em>Optional</em>. For custom emoji stickers, unique identifier of the custom emoji
  * @param needsRepainting
  *   <em>Optional</em>. <em>True</em>, if the sticker must be repainted to a text color in messages, the color of the
  *   Telegram Premium badge in emoji status, white color on chat photos, or another appropriate color in other places
  * @param fileSize
  *   <em>Optional</em>. File size in bytes
  */
final case class Sticker(
  fileId: String,
  fileUniqueId: String,
  `type`: String,
  width: Int,
  height: Int,
  isAnimated: Boolean,
  isVideo: Boolean,
  thumbnail: Option[PhotoSize] = Option.empty,
  emoji: Option[String] = Option.empty,
  setName: Option[String] = Option.empty,
  premiumAnimation: Option[File] = Option.empty,
  maskPosition: Option[MaskPosition] = Option.empty,
  customEmojiId: Option[String] = Option.empty,
  needsRepainting: Option[Boolean] = Option.empty,
  fileSize: Option[Long] = Option.empty
)
