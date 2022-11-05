package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param text
  *   Text of the message to be sent, 1-4096 characters after entities parsing
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
  * @param parseMode
  *   Mode for parsing entities in the message text. See formatting options for more details.
  * @param entities
  *   A JSON-serialized list of special entities that appear in message text, which can be specified instead of
  *   parse_mode
  * @param disableWebPagePreview
  *   Disables link previews for links in this message
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
final case class SendMessageReq(
  chatId: ChatId,
  text: String,
  messageThreadId: Option[Int] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  entities: List[MessageEntity] = List.empty,
  disableWebPagePreview: Option[Boolean] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  replyToMessageId: Option[Int] = Option.empty,
  allowSendingWithoutReply: Option[Boolean] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
