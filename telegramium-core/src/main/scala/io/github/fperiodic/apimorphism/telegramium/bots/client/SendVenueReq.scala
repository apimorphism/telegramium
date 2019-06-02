package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.ChatId
import io.github.fperiodic.apimorphism.telegramium.bots.KeyboardMarkup

final case class SendVenueReq(
                              /** Unique identifier for the target chat or username of the
                                * target channel (in the format @channelusername)*/
                              chatId: ChatId,
                              /** Latitude of the venue*/
                              latitude: Float,
                              /** Longitude of the venue*/
                              longitude: Float,
                              /** Name of the venue*/
                              title: String,
                              /** Address of the venue*/
                              address: String,
                              /** Foursquare identifier of the venue*/
                              foursquareId: Option[String] = Option.empty,
                              /** Foursquare type of the venue, if known. (For example,
                                * “arts_entertainment/default”, “arts_entertainment/aquarium”
                                * or “food/icecream”.)*/
                              foursquareType: Option[String] = Option.empty,
                              /** Sends the message silently. Users will receive a
                                * notification with no sound.*/
                              disableNotification: Option[Boolean] = Option.empty,
                              /** If the message is a reply, ID of the original message*/
                              replyToMessageId: Option[Int] = Option.empty,
                              /** Additional interface options. A JSON-serialized object for
                                * an inline keyboard, custom reply keyboard, instructions to
                                * remove reply keyboard or to force a reply from the user.*/
                              replyMarkup: Option[KeyboardMarkup] = Option.empty)
