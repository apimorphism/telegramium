package telegramium.bots

/** This object represents an animation file (GIF or H.264/MPEG-4 AVC video without sound).
  *
  * @param fileId
  *   Identifier for this file, which can be used to download or reuse the file
  * @param fileUniqueId
  *   Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used
  *   to download or reuse the file.
  * @param width
  *   Video width as defined by the sender
  * @param height
  *   Video height as defined by the sender
  * @param duration
  *   Duration of the video in seconds as defined by the sender
  * @param thumbnail
  *   Optional. Animation thumbnail as defined by the sender
  * @param fileName
  *   Optional. Original animation filename as defined by the sender
  * @param mimeType
  *   Optional. MIME type of the file as defined by the sender
  * @param fileSize
  *   Optional. File size in bytes. It can be bigger than 2&#94;31 and some programming languages may have
  *   difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a signed 64-bit integer
  *   or double-precision float type are safe for storing this value.
  */
final case class Animation(
  fileId: String,
  fileUniqueId: String,
  width: Int,
  height: Int,
  duration: Int,
  thumbnail: Option[PhotoSize] = Option.empty,
  fileName: Option[String] = Option.empty,
  mimeType: Option[String] = Option.empty,
  fileSize: Option[Long] = Option.empty
)
