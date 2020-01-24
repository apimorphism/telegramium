package telegramium.bots

/** This object represents one size of a photo or a file / sticker thumbnail.*/
final case class PhotoSize(
                           /** Identifier for this file, which can be used to download or
                             * reuse the file*/
                           fileId: String,
                           /** Unique identifier for this file, which is supposed to be
                             * the same over time and for different bots. Can't be used to
                             * download or reuse the file.*/
                           fileUniqueId: String,
                           /** Photo width*/
                           width: Int,
                           /** Photo height*/
                           height: Int,
                           /** Optional. File size*/
                           fileSize: Option[Int] = Option.empty)
