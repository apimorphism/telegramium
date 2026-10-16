package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InlineKeyboardMarkup

/** @param latitude
  *   Latitude of new location
  * @param longitude
  *   Longitude of new location
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the message to be edited was sent
  * @param chatId
  *   Required if <em>inline_message_id</em> is not specified. Unique identifier for the target chat or username of the
  *   target bot, supergroup or channel in the format <code>&#064;username</code>.
  * @param messageId
  *   Required if <em>inline_message_id</em> is not specified. Identifier of the message to edit.
  * @param inlineMessageId
  *   Required if <em>chat_id</em> and <em>message_id</em> are not specified. Identifier of the inline message.
  * @param livePeriod
  *   New period in seconds during which the location can be updated, starting from the message send date. If 0x7FFFFFFF
  *   is specified, then the location can be updated forever. Otherwise, the new value must not exceed the current
  *   <em>live_period</em> by more than a day, and the live location expiration date must remain within the next 90
  *   days. If not specified, then <em>live_period</em> remains unchanged.
  * @param horizontalAccuracy
  *   The radius of uncertainty for the location, measured in meters; 0-1500
  * @param heading
  *   Direction in which the user is moving, in degrees. Must be between 1 and 360 if specified.
  * @param proximityAlertRadius
  *   The maximum distance for proximity alerts about approaching another chat member, in meters. Must be between 1 and
  *   100000 if specified.
  * @param replyMarkup
  *   A JSON-serialized object for a new <a href="https://core.telegram.org/bots/features#inline-keyboards">inline
  *   keyboard</a>
  */
final case class EditMessageLiveLocationReq(
  latitude: Float,
  longitude: Float,
  businessConnectionId: Option[String] = Option.empty,
  chatId: Option[ChatId] = Option.empty,
  messageId: Option[Int] = Option.empty,
  inlineMessageId: Option[String] = Option.empty,
  livePeriod: Option[Int] = Option.empty,
  horizontalAccuracy: Option[Float] = Option.empty,
  heading: Option[Int] = Option.empty,
  proximityAlertRadius: Option[Int] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
)
