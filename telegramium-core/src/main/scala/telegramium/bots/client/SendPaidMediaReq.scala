package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InputPaidMedia
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity
import telegramium.bots.ReplyParameters
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param starCount
  *   The number of Telegram Stars that must be paid to buy access to the media
  * @param media
  *   A JSON-serialized array describing the media to be sent; up to 10 items
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
  * @param replyParameters
  *   Description of the message to reply to
  * @param replyMarkup
  *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
  *   to remove a reply keyboard or to force a reply from the user
  */
final case class SendPaidMediaReq(
  chatId: ChatId,
  starCount: Int,
  media: List[InputPaidMedia] = List.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  replyParameters: Option[ReplyParameters] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
