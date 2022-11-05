package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.IFile
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param videoNote
  *   Video note to send. Pass a file_id as String to send a video note that exists on the Telegram servers
  *   (recommended) or upload a new video using multipart/form-data. Sending video notes by a URL is currently
  *   unsupported
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
  * @param duration
  *   Duration of sent video in seconds
  * @param length
  *   Video width and height, i.e. diameter of the video message
  * @param thumb
  *   Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The
  *   thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed
  *   320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only
  *   uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using
  *   multipart/form-data under <file_attach_name>.
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent message from forwarding and saving
  * @param replyToMessageId
  *   If the message is a reply, ID of the original message
  * @param allowSendingWithoutReply
  *   Pass True if the message should be sent even if the specified replied-to message is not found
  * @param replyMarkup
  *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
  *   to remove reply keyboard or to force a reply from the user.
  */
final case class SendVideoNoteReq(
  chatId: ChatId,
  videoNote: IFile,
  messageThreadId: Option[Int] = Option.empty,
  duration: Option[Int] = Option.empty,
  length: Option[Int] = Option.empty,
  thumb: Option[IFile] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  replyToMessageId: Option[Int] = Option.empty,
  allowSendingWithoutReply: Option[Boolean] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
