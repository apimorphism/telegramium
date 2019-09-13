package telegramium.bots

/** This object represents an animation file (GIF or H.264/MPEG-4 AVC video without
  * sound).*/
final case class Animation(
                           /** Identifier for this file*/
                           fileId: String,
                           /** Video width as defined by sender*/
                           width: Int,
                           /** Video height as defined by sender*/
                           height: Int,
                           /** Duration of the video in seconds as defined by sender*/
                           duration: Int,
                           /** Optional. Animation thumbnail as defined by sender*/
                           thumb: Option[PhotoSize] = Option.empty,
                           /** Optional. Original animation filename as defined by sender*/
                           fileName: Option[String] = Option.empty,
                           /** Optional. MIME type of the file as defined by sender*/
                           mimeType: Option[String] = Option.empty,
                           /** Optional. File size*/
                           fileSize: Option[Int] = Option.empty)
