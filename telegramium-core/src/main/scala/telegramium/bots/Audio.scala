package telegramium.bots

/** This object represents an audio file to be treated as music by the Telegram clients.
  *
  * @param fileId
  *   Identifier for this file, which can be used to download or reuse the file
  * @param fileUniqueId
  *   Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used
  *   to download or reuse the file.
  * @param duration
  *   Duration of the audio in seconds as defined by sender
  * @param performer
  *   Optional. Performer of the audio as defined by sender or by audio tags
  * @param title
  *   Optional. Title of the audio as defined by sender or by audio tags
  * @param fileName
  *   Optional. Original filename as defined by sender
  * @param mimeType
  *   Optional. MIME type of the file as defined by sender
  * @param fileSize
  *   Optional. File size in bytes. It can be bigger than 2&#94;31 and some programming languages may have
  *   difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a signed 64-bit integer
  *   or double-precision float type are safe for storing this value.
  * @param thumb
  *   Optional. Thumbnail of the album cover to which the music file belongs
  */
final case class Audio(
  fileId: String,
  fileUniqueId: String,
  duration: Int,
  performer: Option[String] = Option.empty,
  title: Option[String] = Option.empty,
  fileName: Option[String] = Option.empty,
  mimeType: Option[String] = Option.empty,
  fileSize: Option[Long] = Option.empty,
  thumb: Option[PhotoSize] = Option.empty
)
