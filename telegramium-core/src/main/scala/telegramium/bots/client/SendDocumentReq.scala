package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.IFile
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.ReplyParameters
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param document
  *   File to send. Pass a file_id as String to send a file that exists on the Telegram servers (recommended), pass an
  *   HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data.
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
  * @param thumbnail
  *   Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The
  *   thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed
  *   320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only
  *   uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using
  *   multipart/form-data under <file_attach_name>.
  * @param caption
  *   Document caption (may also be used when resending documents by file_id), 0-1024 characters after entities parsing
  * @param parseMode
  *   Mode for parsing entities in the document caption. See formatting options for more details.
  * @param captionEntities
  *   A JSON-serialized list of special entities that appear in the caption, which can be specified instead of
  *   parse_mode
  * @param disableContentTypeDetection
  *   Disables automatic server-side content type detection for files uploaded using multipart/form-data
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent message from forwarding and saving
  * @param replyParameters
  *   Description of the message to reply to
  * @param replyMarkup
  *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
  *   to remove reply keyboard or to force a reply from the user.
  */
final case class SendDocumentReq(
  chatId: ChatId,
  document: IFile,
  messageThreadId: Option[Int] = Option.empty,
  thumbnail: Option[IFile] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  disableContentTypeDetection: Option[Boolean] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  replyParameters: Option[ReplyParameters] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
