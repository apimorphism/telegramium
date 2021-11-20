package telegramium.bots

/** This object represents a voice note.
  *
  * @param fileId
  *   Identifier for this file, which can be used to download or reuse the file
  * @param fileUniqueId
  *   Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used
  *   to download or reuse the file.
  * @param duration
  *   Duration of the audio in seconds as defined by sender
  * @param mimeType
  *   Optional. MIME type of the file as defined by sender
  * @param fileSize
  *   Optional. File size in bytes
  */
final case class Voice(
  fileId: String,
  fileUniqueId: String,
  duration: Int,
  mimeType: Option[String] = Option.empty,
  fileSize: Option[Int] = Option.empty
)
