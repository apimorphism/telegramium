package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.KeyboardMarkup

final case class SendLocationReq(
                                 /** Unique identifier for the target chat or username of the
                                   * target channel (in the format @channelusername)*/
                                 chatId: ChatId,
                                 /** Latitude of the location*/
                                 latitude: Float,
                                 /** Longitude of the location*/
                                 longitude: Float,
                                 /** The radius of uncertainty for the location, measured in
                                   * meters; 0-1500*/
                                 horizontalAccuracy: Option[Float] = Option.empty,
                                 /** Period in seconds for which the location will be updated
                                   * (see Live Locations, should be between 60 and 86400.*/
                                 livePeriod: Option[Int] = Option.empty,
                                 /** For live locations, a direction in which the user is
                                   * moving, in degrees. Must be between 1 and 360 if specified.*/
                                 heading: Option[Int] = Option.empty,
                                 /** For live locations, a maximum distance for proximity alerts
                                   * about approaching another chat member, in meters. Must be
                                   * between 1 and 100000 if specified.*/
                                 proximityAlertRadius: Option[Int] = Option.empty,
                                 /** Sends the message silently. Users will receive a
                                   * notification with no sound.*/
                                 disableNotification: Option[Boolean] = Option.empty,
                                 /** If the message is a reply, ID of the original message*/
                                 replyToMessageId: Option[Int] = Option.empty,
                                 /** Pass True, if the message should be sent even if the
                                   * specified replied-to message is not found*/
                                 allowSendingWithoutReply: Option[Boolean] = Option.empty,
                                 /** Additional interface options. A JSON-serialized object for
                                   * an inline keyboard, custom reply keyboard, instructions to
                                   * remove reply keyboard or to force a reply from the user.*/
                                 replyMarkup: Option[KeyboardMarkup] = Option.empty)
