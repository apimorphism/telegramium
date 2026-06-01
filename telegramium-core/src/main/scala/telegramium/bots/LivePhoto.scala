package telegramium.bots

/** This object represents a live photo.
  *
  * @param fileId
  *   Identifier for the video file which can be used to download or reuse the file
  * @param fileUniqueId
  *   Unique identifier for the video file which is supposed to be the same over time and for different bots. Can't be
  *   used to download or reuse the file.
  * @param width
  *   Video width as defined by the sender
  * @param height
  *   Video height as defined by the sender
  * @param duration
  *   Duration of the video in seconds as defined by the sender
  * @param photo
  *   Optional. Available sizes of the corresponding static photo
  * @param mimeType
  *   Optional. MIME type of the file as defined by the sender
  * @param fileSize
  *   Optional. File size in bytes. It can be bigger than 2&#94;31 and some programming languages may have
  *   difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a signed 64-bit integer
  *   or double-precision float type are safe for storing this value.
  */
final case class LivePhoto(
  fileId: String,
  fileUniqueId: String,
  width: Int,
  height: Int,
  duration: Int,
  photo: List[PhotoSize] = List.empty,
  mimeType: Option[String] = Option.empty,
  fileSize: Option[Long] = Option.empty
)
