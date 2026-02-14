package telegramium.bots

/** This object represents a video file of a specific quality.
  *
  * @param fileId
  *   Identifier for this file, which can be used to download or reuse the file
  * @param fileUniqueId
  *   Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used
  *   to download or reuse the file.
  * @param width
  *   Video width
  * @param height
  *   Video height
  * @param codec
  *   Codec that was used to encode the video, for example, “h264”, “h265”, or “av01”
  * @param fileSize
  *   Optional. File size in bytes. It can be bigger than 2&#94;31 and some programming languages may have
  *   difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a signed 64-bit integer
  *   or double-precision float type are safe for storing this value.
  */
final case class VideoQuality(
  fileId: String,
  fileUniqueId: String,
  width: Int,
  height: Int,
  codec: String,
  fileSize: Option[Long] = Option.empty
)
