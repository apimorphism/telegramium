package telegramium.bots

/** This object represents an audio file to be treated as music by the Telegram
  * clients.*/
final case class Audio(
                       /** Identifier for this file*/
                       fileId: String,
                       /** Duration of the audio in seconds as defined by sender*/
                       duration: Int,
                       /** Optional. Performer of the audio as defined by sender or by
                         * audio tags*/
                       performer: Option[String] = Option.empty,
                       /** Optional. Title of the audio as defined by sender or by
                         * audio tags*/
                       title: Option[String] = Option.empty,
                       /** Optional. MIME type of the file as defined by sender*/
                       mimeType: Option[String] = Option.empty,
                       /** Optional. File size*/
                       fileSize: Option[Int] = Option.empty,
                       /** Optional. Thumbnail of the album cover to which the music
                         * file belongs*/
                       thumb: Option[PhotoSize] = Option.empty)
