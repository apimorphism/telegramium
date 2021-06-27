package telegramium.bots

/** This object represents an animation file (GIF or H.264/MPEG-4 AVC video without sound).
  *
  * @param fileId
  *   Identifier for this file, which can be used to download or reuse the file
  * @param fileUniqueId
  *   Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used
  *   to download or reuse the file.
  * @param width
  *   Video width as defined by sender
  * @param height
  *   Video height as defined by sender
  * @param duration
  *   Duration of the video in seconds as defined by sender
  * @param thumb
  *   Optional. Animation thumbnail as defined by sender
  * @param fileName
  *   Optional. Original animation filename as defined by sender
  * @param mimeType
  *   Optional. MIME type of the file as defined by sender
  * @param fileSize
  *   Optional. File size
  */
final case class Animation(
  fileId: String,
  fileUniqueId: String,
  width: Int,
  height: Int,
  duration: Int,
  thumb: Option[PhotoSize] = Option.empty,
  fileName: Option[String] = Option.empty,
  mimeType: Option[String] = Option.empty,
  fileSize: Option[Int] = Option.empty
)
