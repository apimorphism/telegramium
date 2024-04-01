package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ReplyParameters
import telegramium.bots.KeyboardMarkup

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param latitude
  *   Latitude of the venue
  * @param longitude
  *   Longitude of the venue
  * @param title
  *   Name of the venue
  * @param address
  *   Address of the venue
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message will be sent
  * @param messageThreadId
  *   Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
  * @param foursquareId
  *   Foursquare identifier of the venue
  * @param foursquareType
  *   Foursquare type of the venue, if known. (For example, “arts_entertainment/default”, “arts_entertainment/aquarium”
  *   or “food/icecream”.)
  * @param googlePlaceId
  *   Google Places identifier of the venue
  * @param googlePlaceType
  *   Google Places type of the venue. (See supported types.)
  * @param disableNotification
  *   Sends the message silently. Users will receive a notification with no sound.
  * @param protectContent
  *   Protects the contents of the sent message from forwarding and saving
  * @param replyParameters
  *   Description of the message to reply to
  * @param replyMarkup
  *   Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions
  *   to remove a reply keyboard or to force a reply from the user. Not supported for messages sent on behalf of a
  *   business account
  */
final case class SendVenueReq(
  chatId: ChatId,
  latitude: Float,
  longitude: Float,
  title: String,
  address: String,
  businessConnectionId: Option[String] = Option.empty,
  messageThreadId: Option[Int] = Option.empty,
  foursquareId: Option[String] = Option.empty,
  foursquareType: Option[String] = Option.empty,
  googlePlaceId: Option[String] = Option.empty,
  googlePlaceType: Option[String] = Option.empty,
  disableNotification: Option[Boolean] = Option.empty,
  protectContent: Option[Boolean] = Option.empty,
  replyParameters: Option[ReplyParameters] = Option.empty,
  replyMarkup: Option[KeyboardMarkup] = Option.empty
)
