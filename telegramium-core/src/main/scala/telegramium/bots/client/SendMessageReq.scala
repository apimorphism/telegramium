package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.EphemeralMessageParameters
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.LinkPreviewOptions
import telegramium.bots.SuggestedPostParameters
import telegramium.bots.ReplyParameters
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target bot, supergroup or channel in the format
  *   <code>&#064;username</code>
  * @param text
  *   Text of the message to be sent, 1-4096 characters after entities parsing
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message will be sent
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of a forum; for forum supergroups and private chats of
  *   bots with forum topic mode enabled only
  * @param directMessagesTopicId
  *   Identifier of the direct messages topic to which the message will be sent; required if the message is sent to a
  *   direct messages chat
  * @param ephemeralMessageParameters
  *   A JSON-serialized object containing the parameters of the ephemeral message to send
  * @param parseMode
  *   Mode for parsing entities in the message text. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param entities
  *   A JSON-serialized list of special entities that appear in message text, which can be specified instead of
  *   <em>parse_mode</em>
  * @param linkPreviewOptions
  *   Link preview generation options for the message
  * @param disableNotification
  *   Sends the message <a href="https://telegram.org/blog/channels-2-0#silent-messages">silently</a>. Users will
  *   receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent message from forwarding and saving
  * @param allowPaidBroadcast
  *   Pass <em>True</em> to allow up to 1000 messages per second, ignoring <a
  *   href="https://core.telegram.org/bots/faq#how-can-i-message-all-of-my-bot-39s-subscribers-at-once">broadcasting
  *   limits</a> for a fee of 0.1 Telegram Stars per message. The relevant Stars will be withdrawn from the bot's
  *   balance.
  * @param messageEffectId
  *   Unique identifier of the message effect to be added to the message; for private chats only
  * @param suggestedPostParameters
  *   A JSON-serialized object containing the parameters of the suggested post to send; for direct messages chats only.
  *   If the message is sent as a reply to another suggested post, then that suggested post is automatically declined.
  * @param replyParameters
  *   Description of the message to reply to
  * @param replyMarkup
  *   Additional interface options. A JSON-serialized object for an <a
  *   href="https://core.telegram.org/bots/features#inline-keyboards">inline keyboard</a>, <a
  *   href="https://core.telegram.org/bots/features#keyboards">custom reply keyboard</a>, instructions to remove a reply
  *   keyboard or to force a reply from the user.
  */
final case class SendMessageReq(
  chatId: ChatId,
  text: String,
  businessConnectionId: Option[String] = Option.empty,
  messageThreadId: Option[Int] = Option.empty,
  directMessagesTopicId: Option[Long] = Option.empty,
  ephemeralMessageParameters: Option[EphemeralMessageParameters] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  entities: List[MessageEntity] = List.empty,
  linkPreviewOptions: Option[LinkPreviewOptions] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  allowPaidBroadcast: Option[Boolean] = Option.empty,
  messageEffectId: Option[String] = Option.empty,
  suggestedPostParameters: Option[SuggestedPostParameters] = Option.empty,
  replyParameters: Option[ReplyParameters] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
