package telegramium.bots

/** This object represents a voice note.
  *
  * @param fileId
  *   Identifier for this file, which can be used to download or reuse the file
  * @param fileUniqueId
  *   Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used
  *   to download or reuse the file.
  * @param duration
  *   Duration of the audio in seconds as defined by the sender
  * @param mimeType
  *   Optional. MIME type of the file as defined by the sender
  * @param fileSize
  *   Optional. File size in bytes. It can be bigger than 2&#94;31 and some programming languages may have
  *   difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a signed 64-bit integer
  *   or double-precision float type are safe for storing this value.
  */
final case class Voice(
  fileId: String,
  fileUniqueId: String,
  duration: Int,
  mimeType: Option[String] = Option.empty,
  fileSize: Option[Long] = Option.empty
)
