package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InlineKeyboardMarkup

/** @param latitude
  *   Latitude of new location
  * @param longitude
  *   Longitude of new location
  * @param chatId
  *   Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target
  *   channel (in the format &#064;channelusername)
  * @param messageId
  *   Required if inline_message_id is not specified. Identifier of the message to edit
  * @param inlineMessageId
  *   Required if chat_id and message_id are not specified. Identifier of the inline message
  * @param horizontalAccuracy
  *   The radius of uncertainty for the location, measured in meters; 0-1500
  * @param heading
  *   Direction in which the user is moving, in degrees. Must be between 1 and 360 if specified.
  * @param proximityAlertRadius
  *   The maximum distance for proximity alerts about approaching another chat member, in meters. Must be between 1 and
  *   100000 if specified.
  * @param replyMarkup
  *   A JSON-serialized object for a new inline keyboard.
  */
final case class EditMessageLiveLocationReq(
  latitude: Float,
  longitude: Float,
  chatId: Option[ChatId] = Option.empty,
  messageId: Option[Int] = Option.empty,
  inlineMessageId: Option[String] = Option.empty,
  horizontalAccuracy: Option[Float] = Option.empty,
  heading: Option[Int] = Option.empty,
  proximityAlertRadius: Option[Int] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
