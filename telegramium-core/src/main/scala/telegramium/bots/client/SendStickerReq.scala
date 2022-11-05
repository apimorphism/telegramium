package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.IFile
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param sticker
  *   Sticker to send. Pass a file_id as String to send a file that exists on the Telegram servers (recommended), pass
  *   an HTTP URL as a String for Telegram to get a .WEBP file from the Internet, or upload a new one using
  *   multipart/form-data.
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
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
final case class SendStickerReq(
  chatId: ChatId,
  sticker: IFile,
  messageThreadId: Option[Int] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  replyToMessageId: Option[Int] = Option.empty,
  allowSendingWithoutReply: Option[Boolean] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
