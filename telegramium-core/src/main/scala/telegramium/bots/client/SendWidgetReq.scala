package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.ReplyParameters
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup in the format
  *   <code>&#064;username</code>
  * @param html
  *   HTML code of the widget, 1-32768 bytes. The code must not exceed 4096 bytes when compressed by zlib with the
  *   maximum compression level.
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of a forum; for forum supergroups and private chats of
  *   bots with forum topic mode enabled only
  * @param width
  *   Width of the widget in logical pixels; omit if the widget must occupy the whole width of the message bubble. The
  *   width is clamped by the app to the range from 200 to the maximum width of the message bubble.
  * @param height
  *   Height of the widget in logical pixels; omit if the height of the widget isn't fixed. The height is clamped by the
  *   app to the range 50-600.
  * @param publicData
  *   Public data of the widget, 0-1024 bytes, which will be available wherever the widget is shown. If the widget is
  *   shared, then all messages with it will have the same public data.
  * @param chatData
  *   Data of the widget, which is specific to the message, 0-1024 bytes
  * @param caption
  *   Widget caption, 0-1024 characters after entities parsing
  * @param parseMode
  *   Mode for parsing entities in the widget caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   A JSON-serialized list of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param showCaptionAboveMedia
  *   Pass <em>True</em> if the caption must be shown above the widget
  * @param allowQueries
  *   Pass <em>True</em> to allow the widget to send queries to the bot
  * @param isShared
  *   Pass <em>True</em> to create a shared widget, which can be sent again using the method <a
  *   href="https://core.telegram.org/bots/api#sendsharedwidget">sendSharedWidget</a>
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
  * @param replyParameters
  *   Description of the message to reply to
  * @param replyMarkup
  *   Additional interface options. A JSON-serialized object for an <a
  *   href="https://core.telegram.org/bots/features#inline-keyboards">inline keyboard</a>, <a
  *   href="https://core.telegram.org/bots/features#keyboards">custom reply keyboard</a>, instructions to remove a reply
  *   keyboard or to force a reply from the user.
  */
final case class SendWidgetReq(
  chatId: ChatId,
  html: String,
  messageThreadId: Option[Int] = Option.empty,
  width: Option[Int] = Option.empty,
  height: Option[Int] = Option.empty,
  publicData: Option[String] = Option.empty,
  chatData: Option[String] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  allowQueries: Option[Boolean] = Option.empty,
  isShared: Option[Boolean] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  allowPaidBroadcast: Option[Boolean] = Option.empty,
  messageEffectId: Option[String] = Option.empty,
  replyParameters: Option[ReplyParameters] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
