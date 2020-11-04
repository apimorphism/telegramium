package telegramium.bots

/** This object represents a video file.*/
final case class Video(
                       /** Identifier for this file, which can be used to download or
                         * reuse the file*/
                       fileId: String,
                       /** Unique identifier for this file, which is supposed to be
                         * the same over time and for different bots. Can't be used to
                         * download or reuse the file.*/
                       fileUniqueId: String,
                       /** Video width as defined by sender*/
                       width: Int,
                       /** Video height as defined by sender*/
                       height: Int,
                       /** Duration of the video in seconds as defined by sender*/
                       duration: Int,
                       /** Optional. Video thumbnail*/
                       thumb: Option[PhotoSize] = Option.empty,
                       /** Optional. Original filename as defined by sender*/
                       fileName: Option[String] = Option.empty,
                       /** Optional. Mime type of a file as defined by sender*/
                       mimeType: Option[String] = Option.empty,
                       /** Optional. File size*/
                       fileSize: Option[Int] = Option.empty)
