package telegramium.bots

/** This object represents one size of a photo or a <a href="https://core.telegram.org/bots/api#document">file</a> / <a
  * href="https://core.telegram.org/bots/api#sticker">sticker</a> thumbnail.
  *
  * @param fileId
  *   Identifier for this file, which can be used to download or reuse the file
  * @param fileUniqueId
  *   Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used
  *   to download or reuse the file.
  * @param width
  *   Photo width
  * @param height
  *   Photo height
  * @param fileSize
  *   <em>Optional</em>. File size in bytes
  */
final case class PhotoSize(
  fileId: String,
  fileUniqueId: String,
  width: Int,
  height: Int,
  fileSize: Option[Long] = Option.empty
)
