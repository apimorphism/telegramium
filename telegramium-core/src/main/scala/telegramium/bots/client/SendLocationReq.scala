package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ReplyParameters
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param latitude
  *   Latitude of the location
  * @param longitude
  *   Longitude of the location
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message will be sent
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
  * @param horizontalAccuracy
  *   The radius of uncertainty for the location, measured in meters; 0-1500
  * @param livePeriod
  *   Period in seconds during which the location will be updated (see Live Locations, should be between 60 and 86400,
  *   or 0x7FFFFFFF for live locations that can be edited indefinitely.
  * @param heading
  *   For live locations, a direction in which the user is moving, in degrees. Must be between 1 and 360 if specified.
  * @param proximityAlertRadius
  *   For live locations, a maximum distance for proximity alerts about approaching another chat member, in meters. Must
  *   be between 1 and 100000 if specified.
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
final case class SendLocationReq(
  chatId: ChatId,
  latitude: Float,
  longitude: Float,
  businessConnectionId: Option[String] = Option.empty,
  messageThreadId: Option[Int] = Option.empty,
  horizontalAccuracy: Option[Float] = Option.empty,
  livePeriod: Option[Int] = Option.empty,
  heading: Option[Int] = Option.empty,
  proximityAlertRadius: Option[Int] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  replyParameters: Option[ReplyParameters] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
