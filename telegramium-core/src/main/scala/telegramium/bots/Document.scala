package telegramium.bots

/** This object represents a general file (as opposed to photos, voice messages and
  * audio files).*/
final case class Document(
                          /** Identifier for this file, which can be used to download or
                            * reuse the file*/
                          fileId: String,
                          /** Unique identifier for this file, which is supposed to be
                            * the same over time and for different bots. Can't be used to
                            * download or reuse the file.*/
                          fileUniqueId: String,
                          /** Optional. Document thumbnail as defined by sender*/
                          thumb: Option[PhotoSize] = Option.empty,
                          /** Optional. Original filename as defined by sender*/
                          fileName: Option[String] = Option.empty,
                          /** Optional. MIME type of the file as defined by sender*/
                          mimeType: Option[String] = Option.empty,
                          /** Optional. File size*/
                          fileSize: Option[Int] = Option.empty)
