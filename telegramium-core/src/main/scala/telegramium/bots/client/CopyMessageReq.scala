package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.SuggestedPostParameters
import telegramium.bots.ReplyParameters
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param fromChatId
  *   Unique identifier for the chat where the original message was sent (or channel username in the format
  *   &#064;channelusername)
  * @param messageId
  *   Message identifier in the chat specified in from_chat_id
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of a forum; for forum supergroups and private chats of
  *   bots with forum topic mode enabled only
  * @param directMessagesTopicId
  *   Identifier of the direct messages topic to which the message will be sent; required if the message is sent to a
  *   direct messages chat
  * @param videoStartTimestamp
  *   New start timestamp for the copied video in the message
  * @param caption
  *   New caption for media, 0-1024 characters after entities parsing. If not specified, the original caption is kept
  * @param parseMode
  *   Mode for parsing entities in the new caption. See formatting options for more details.
  * @param captionEntities
  *   A JSON-serialized list of special entities that appear in the new caption, which can be specified instead of
  *   parse_mode
  * @param showCaptionAboveMedia
  *   Pass True, if the caption must be shown above the message media. Ignored if a new caption isn't specified.
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent message from forwarding and saving
  * @param allowPaidBroadcast
  *   Pass True to allow up to 1000 messages per second, ignoring broadcasting limits for a fee of 0.1 Telegram Stars
  *   per message. The relevant Stars will be withdrawn from the bot's balance
  * @param messageEffectId
  *   Unique identifier of the message effect to be added to the message; only available when copying to private chats
  * @param suggestedPostParameters
  *   A JSON-serialized object containing the parameters of the suggested post to send; for direct messages chats only.
  *   If the message is sent as a reply to another suggested post, then that suggested post is automatically declined.
  * @param replyParameters
  *   Description of the message to reply to
  * @param replyMarkup
  *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
  *   to remove a reply keyboard or to force a reply from the user
  */
final case class CopyMessageReq(
  chatId: ChatId,
  fromChatId: ChatId,
  messageId: Int,
  messageThreadId: Option[Int] = Option.empty,
  directMessagesTopicId: Option[Long] = Option.empty,
  videoStartTimestamp: Option[Int] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  allowPaidBroadcast: Option[Boolean] = Option.empty,
  messageEffectId: Option[String] = Option.empty,
  suggestedPostParameters: Option[SuggestedPostParameters] = Option.empty,
  replyParameters: Option[ReplyParameters] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
