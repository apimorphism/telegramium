package telegramium.bots

/** This object represents a voice note.*/
final case class Voice(
                       /** Identifier for this file*/
                       fileId: String,
                       /** Duration of the audio in seconds as defined by sender*/
                       duration: Int,
                       /** Optional. MIME type of the file as defined by sender*/
                       mimeType: Option[String] = Option.empty,
                       /** Optional. File size*/
                       fileSize: Option[Int] = Option.empty)
