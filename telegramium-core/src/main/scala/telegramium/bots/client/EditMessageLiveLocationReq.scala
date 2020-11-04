package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.InlineKeyboardMarkup

final case class EditMessageLiveLocationReq(
    /** Required if inline_message_id is not specified. Unique
      * identifier for the target chat or username of the target
      * channel (in the format @channelusername)*/
    chatId: Option[ChatId] = Option.empty,
    /** Required if inline_message_id is not specified. Identifier
      * of the message to edit*/
    messageId: Option[Int] = Option.empty,
    /** Required if chat_id and message_id are not specified.
      * Identifier of the inline message*/
    inlineMessageId: Option[String] = Option.empty,
    /** Latitude of new location*/
    latitude: Float,
    /** Longitude of new location*/
    longitude: Float,
    /** The radius of uncertainty for the location, measured in
      * meters; 0-1500*/
    horizontalAccuracy: Option[Float] = Option.empty,
    /** Direction in which the user is moving, in degrees. Must be
      * between 1 and 360 if specified.*/
    heading: Option[Int] = Option.empty,
    /** Maximum distance for proximity alerts about approaching
      * another chat member, in meters. Must be between 1 and 100000
      * if specified.*/
    proximityAlertRadius: Option[Int] = Option.empty,
    /** A JSON-serialized object for a new inline keyboard.*/
    replyMarkup: Option[InlineKeyboardMarkup] = Option.empty)
