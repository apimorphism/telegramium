package telegramium.bots

/** This object represents a video message (available in Telegram apps as of v.4.0).
  *
  * @param fileId
  *   Identifier for this file, which can be used to download or reuse the file
  * @param fileUniqueId
  *   Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used
  *   to download or reuse the file.
  * @param length
  *   Video width and height (diameter of the video message) as defined by sender
  * @param duration
  *   Duration of the video in seconds as defined by sender
  * @param thumb
  *   Optional. Video thumbnail
  * @param fileSize
  *   Optional. File size in bytes
  */
final case class VideoNote(
  fileId: String,
  fileUniqueId: String,
  length: Int,
  duration: Int,
  thumb: Option[PhotoSize] = Option.empty,
  fileSize: Option[Int] = Option.empty
)
