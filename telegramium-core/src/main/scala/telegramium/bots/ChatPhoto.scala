package telegramium.bots

/** This object represents a chat photo.*/
final case class ChatPhoto(
                           /** File identifier of small (160x160) chat photo. This file_id
                             * can be used only for photo download and only for as long as
                             * the photo is not changed.*/
                           smallFileId: String,
                           /** Unique file identifier of small (160x160) chat photo, which
                             * is supposed to be the same over time and for different bots.
                             * Can't be used to download or reuse the file.*/
                           smallFileUniqueId: String,
                           /** File identifier of big (640x640) chat photo. This file_id
                             * can be used only for photo download and only for as long as
                             * the photo is not changed.*/
                           bigFileId: String,
                           /** Unique file identifier of big (640x640) chat photo, which
                             * is supposed to be the same over time and for different bots.
                             * Can't be used to download or reuse the file.*/
                           bigFileUniqueId: String)
