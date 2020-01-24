package telegramium.bots

/** This object represents a voice note.*/
final case class Voice(
                       /** Identifier for this file, which can be used to download or
                         * reuse the file*/
                       fileId: String,
                       /** Unique identifier for this file, which is supposed to be
                         * the same over time and for different bots. Can't be used to
                         * download or reuse the file.*/
                       fileUniqueId: String,
                       /** Duration of the audio in seconds as defined by sender*/
                       duration: Int,
                       /** Optional. MIME type of the file as defined by sender*/
                       mimeType: Option[String] = Option.empty,
                       /** Optional. File size*/
                       fileSize: Option[Int] = Option.empty)
