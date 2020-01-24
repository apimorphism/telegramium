package telegramium.bots

/** This object represents a video message (available in Telegram apps as of
  * v.4.0).*/
final case class VideoNote(
                           /** Identifier for this file, which can be used to download or
                             * reuse the file*/
                           fileId: String,
                           /** Unique identifier for this file, which is supposed to be
                             * the same over time and for different bots. Can't be used to
                             * download or reuse the file.*/
                           fileUniqueId: String,
                           /** Video width and height (diameter of the video message) as
                             * defined by sender*/
                           length: Int,
                           /** Duration of the video in seconds as defined by sender*/
                           duration: Int,
                           /** Optional. Video thumbnail*/
                           thumb: Option[PhotoSize] = Option.empty,
                           /** Optional. File size*/
                           fileSize: Option[Int] = Option.empty)
