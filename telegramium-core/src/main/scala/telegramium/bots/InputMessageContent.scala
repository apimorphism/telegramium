package telegramium.bots

sealed trait InputMessageContent {}

/** Represents the content of a venue message to be sent as the result of an inline
  * query.*/
final case class InputVenueMessageContent(
                                          /** Latitude of the venue in degrees*/
                                          latitude: Float,
                                          /** Longitude of the venue in degrees*/
                                          longitude: Float,
                                          /** Name of the venue*/
                                          title: String,
                                          /** Address of the venue*/
                                          address: String,
                                          /** Optional. Foursquare identifier of the venue, if known*/
                                          foursquareId: Option[String] = Option.empty,
                                          /** Optional. Foursquare type of the venue, if known. (For
                                            * example, “arts_entertainment/default”,
                                            * “arts_entertainment/aquarium” or “food/icecream”.)*/
                                          foursquareType: Option[String] = Option.empty,
                                          /** Optional. Google Places identifier of the venue*/
                                          googlePlaceId: Option[String] = Option.empty,
                                          /** Optional. Google Places type of the venue. (See supported
                                            * types.)*/
                                          googlePlaceType: Option[String] = Option.empty)
    extends InputMessageContent

/** Represents the content of a contact message to be sent as the result of an
  * inline query.*/
final case class InputContactMessageContent(
                                            /** Contact's phone number*/
                                            phoneNumber: String,
                                            /** Contact's first name*/
                                            firstName: String,
                                            /** Optional. Contact's last name*/
                                            lastName: Option[String] = Option.empty,
                                            /** Optional. Additional data about the contact in the form of
                                              * a vCard, 0-2048 bytes*/
                                            vcard: Option[String] = Option.empty)
    extends InputMessageContent

/** Represents the content of a location message to be sent as the result of an
  * inline query.*/
final case class InputLocationMessageContent(
                                             /** Latitude of the location in degrees*/
                                             latitude: Float,
                                             /** Longitude of the location in degrees*/
                                             longitude: Float,
                                             /** Optional. The radius of uncertainty for the location,
                                               * measured in meters; 0-1500*/
                                             horizontalAccuracy: Option[Float] = Option.empty,
                                             /** Optional. Period in seconds for which the location can be
                                               * updated, should be between 60 and 86400.*/
                                             livePeriod: Option[Int] = Option.empty,
                                             /** Optional. For live locations, a direction in which the user
                                               * is moving, in degrees. Must be between 1 and 360 if
                                               * specified.*/
                                             heading: Option[Int] = Option.empty,
                                             /** Optional. For live locations, a maximum distance for
                                               * proximity alerts about approaching another chat member, in
                                               * meters. Must be between 1 and 100000 if specified.*/
                                             proximityAlertRadius: Option[Int] = Option.empty)
    extends InputMessageContent

/** Represents the content of a text message to be sent as the result of an inline
  * query.*/
final case class InputTextMessageContent(
                                         /** Text of the message to be sent, 1-4096 characters*/
                                         messageText: String,
                                         /** Optional. Mode for parsing entities in the message text.
                                           * See formatting options for more details.*/
                                         parseMode: Option[ParseMode] = Option.empty,
                                         /** Optional. List of special entities that appear in message
                                           * text, which can be specified instead of parse_mode*/
                                         entities: List[MessageEntity] = List.empty,
                                         /** Optional. Disables link previews for links in the sent
                                           * message*/
                                         disableWebPagePreview: Option[Boolean] = Option.empty)
    extends InputMessageContent
