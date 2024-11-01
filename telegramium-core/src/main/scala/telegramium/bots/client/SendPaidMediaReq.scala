package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InputPaidMedia
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.ReplyParameters
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername). If
  *   the chat is a channel, all Telegram Star proceeds from this media will be credited to the chat's balance.
  *   Otherwise, they will be credited to the bot's balance.
  * @param starCount
  *   The number of Telegram Stars that must be paid to buy access to the media; 1-2500
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message will be sent
  * @param media
  *   A JSON-serialized array describing the media to be sent; up to 10 items
  * @param payload
  *   Bot-defined paid media payload, 0-128 bytes. This will not be displayed to the user, use it for your internal
  *   processes.
  * @param caption
  *   Media caption, 0-1024 characters after entities parsing
  * @param parseMode
  *   Mode for parsing entities in the media caption. See formatting options for more details.
  * @param captionEntities
  *   A JSON-serialized list of special entities that appear in the caption, which can be specified instead of
  *   parse_mode
  * @param showCaptionAboveMedia
  *   Pass True, if the caption must be shown above the message media
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent message from forwarding and saving
  * @param allowPaidBroadcast
  *   Pass True to allow up to 1000 messages per second, ignoring broadcasting limits for a fee of 0.1 Telegram Stars
  *   per message. The relevant Stars will be withdrawn from the bot's balance
  * @param replyParameters
  *   Description of the message to reply to
  * @param replyMarkup
  *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
  *   to remove a reply keyboard or to force a reply from the user
  */
final case class SendPaidMediaReq(
  chatId: ChatId,
  starCount: Int,
  businessConnectionId: Option[String] = Option.empty,
  media: List[InputPaidMedia] = List.empty,
  payload: Option[String] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  allowPaidBroadcast: Option[Boolean] = Option.empty,
  replyParameters: Option[ReplyParameters] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
