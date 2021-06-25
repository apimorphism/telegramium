package telegramium.bots

/**
 * This object represents a general file (as opposed to photos, voice messages and
 * audio files).
 *
 * @param fileId Identifier for this file, which can be used to download or
 * reuse the file
 * @param fileUniqueId Unique identifier for this file, which is supposed to be
 * the same over time and for different bots. Can't be used to
 * download or reuse the file.
 * @param thumb Optional. Document thumbnail as defined by sender
 * @param fileName Optional. Original filename as defined by sender
 * @param mimeType Optional. MIME type of the file as defined by sender
 * @param fileSize Optional. File size
 */
final case class Document(fileId: String,
                          fileUniqueId: String,
                          thumb: Option[PhotoSize] = Option.empty,
                          fileName: Option[String] = Option.empty,
                          mimeType: Option[String] = Option.empty,
                          fileSize: Option[Int] = Option.empty
)
