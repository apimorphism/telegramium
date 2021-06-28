package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.IFile
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param video
  *   Video to send. Pass a file_id as String to send a video that exists on the Telegram servers (recommended), pass an
  *   HTTP URL as a String for Telegram to get a video from the Internet, or upload a new video using
  *   multipart/form-data.
  * @param duration
  *   Duration of sent video in seconds
  * @param width
  *   Video width
  * @param height
  *   Video height
  * @param thumb
  *   Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The
  *   thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed
  *   320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only
  *   uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using
  *   multipart/form-data under <file_attach_name>.
  * @param caption
  *   Video caption (may also be used when resending videos by file_id), 0-1024 characters after entities parsing
  * @param parseMode
  *   Mode for parsing entities in the video caption. See formatting options for more details.
  * @param captionEntities
  *   List of special entities that appear in the caption, which can be specified instead of parse_mode
  * @param supportsStreaming
  *   Pass True, if the uploaded video is suitable for streaming
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param replyToMessageId
  *   If the message is a reply, ID of the original message
  * @param allowSendingWithoutReply
  *   Pass True, if the message should be sent even if the specified replied-to message is not found
  * @param replyMarkup
  *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
  *   to remove reply keyboard or to force a reply from the user.
  */
final case class SendVideoReq(
  chatId: ChatId,
  video: IFile,
  duration: Option[Int] = Option.empty,
  width: Option[Int] = Option.empty,
  height: Option[Int] = Option.empty,
  thumb: Option[IFile] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  supportsStreaming: Option[Boolean] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  replyToMessageId: Option[Int] = Option.empty,
  allowSendingWithoutReply: Option[Boolean] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
