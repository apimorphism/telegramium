package telegramium.bots

/** This object represents a video file.*/
final case class Video(
                       /** Unique identifier for this file*/
                       fileId: String,
                       /** Video width as defined by sender*/
                       width: Int,
                       /** Video height as defined by sender*/
                       height: Int,
                       /** Duration of the video in seconds as defined by sender*/
                       duration: Int,
                       /** Optional. Video thumbnail*/
                       thumb: Option[PhotoSize] = Option.empty,
                       /** Optional. Mime type of a file as defined by sender*/
                       mimeType: Option[String] = Option.empty,
                       /** Optional. File size*/
                       fileSize: Option[Int] = Option.empty)
