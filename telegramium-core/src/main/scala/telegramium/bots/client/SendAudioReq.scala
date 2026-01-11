package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.IFile
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.SuggestedPostParameters
import telegramium.bots.ReplyParameters
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param audio
  *   Audio file to send. Pass a file_id as String to send an audio file that exists on the Telegram servers
  *   (recommended), pass an HTTP URL as a String for Telegram to get an audio file from the Internet, or upload a new
  *   one using multipart/form-data.
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message will be sent
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of a forum; for forum supergroups and private chats of
  *   bots with forum topic mode enabled only
  * @param directMessagesTopicId
  *   Identifier of the direct messages topic to which the message will be sent; required if the message is sent to a
  *   direct messages chat
  * @param caption
  *   Audio caption, 0-1024 characters after entities parsing
  * @param parseMode
  *   Mode for parsing entities in the audio caption. See formatting options for more details.
  * @param captionEntities
  *   A JSON-serialized list of special entities that appear in the caption, which can be specified instead of
  *   parse_mode
  * @param duration
  *   Duration of the audio in seconds
  * @param performer
  *   Performer
  * @param title
  *   Track name
  * @param thumbnail
  *   Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The
  *   thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed
  *   320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only
  *   uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using
  *   multipart/form-data under <file_attach_name>.
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent message from forwarding and saving
  * @param allowPaidBroadcast
  *   Pass True to allow up to 1000 messages per second, ignoring broadcasting limits for a fee of 0.1 Telegram Stars
  *   per message. The relevant Stars will be withdrawn from the bot's balance
  * @param messageEffectId
  *   Unique identifier of the message effect to be added to the message; for private chats only
  * @param suggestedPostParameters
  *   A JSON-serialized object containing the parameters of the suggested post to send; for direct messages chats only.
  *   If the message is sent as a reply to another suggested post, then that suggested post is automatically declined.
  * @param replyParameters
  *   Description of the message to reply to
  * @param replyMarkup
  *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
  *   to remove a reply keyboard or to force a reply from the user
  */
final case class SendAudioReq(
  chatId: ChatId,
  audio: IFile,
  businessConnectionId: Option[String] = Option.empty,
  messageThreadId: Option[Int] = Option.empty,
  directMessagesTopicId: Option[Long] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  duration: Option[Int] = Option.empty,
  performer: Option[String] = Option.empty,
  title: Option[String] = Option.empty,
  thumbnail: Option[IFile] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  allowPaidBroadcast: Option[Boolean] = Option.empty,
  messageEffectId: Option[String] = Option.empty,
  suggestedPostParameters: Option[SuggestedPostParameters] = Option.empty,
  replyParameters: Option[ReplyParameters] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
