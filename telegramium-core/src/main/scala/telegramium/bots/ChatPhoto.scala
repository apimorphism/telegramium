package telegramium.bots

/** This object represents a chat photo.*/
final case class ChatPhoto(
                           /** File identifier of small (160x160) chat photo. This file_id
                             * can be used only for photo download and only for as long as
                             * the photo is not changed.*/
                           smallFileId: String,
                           /** File identifier of big (640x640) chat photo. This file_id
                             * can be used only for photo download and only for as long as
                             * the photo is not changed.*/
                           bigFileId: String)
