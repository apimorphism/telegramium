package telegramium.bots

/** This object represents a general file (as opposed to <a
  * href="https://core.telegram.org/bots/api#photosize">photos</a>, <a
  * href="https://core.telegram.org/bots/api#voice">voice messages</a> and <a
  * href="https://core.telegram.org/bots/api#audio">audio files</a>).
  *
  * @param fileId
  *   Identifier for this file, which can be used to download or reuse the file
  * @param fileUniqueId
  *   Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used
  *   to download or reuse the file.
  * @param thumbnail
  *   <em>Optional</em>. Document thumbnail as defined by the sender
  * @param fileName
  *   <em>Optional</em>. Original filename as defined by the sender
  * @param mimeType
  *   <em>Optional</em>. MIME type of the file as defined by the sender
  * @param fileSize
  *   <em>Optional</em>. File size in bytes. It can be bigger than 2&#94;31 and some programming languages may have
  *   difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a signed 64-bit integer
  *   or double-precision float type are safe for storing this value.
  */
final case class Document(
  fileId: String,
  fileUniqueId: String,
  thumbnail: Option[PhotoSize] = Option.empty,
  fileName: Option[String] = Option.empty,
  mimeType: Option[String] = Option.empty,
  fileSize: Option[Long] = Option.empty
)
