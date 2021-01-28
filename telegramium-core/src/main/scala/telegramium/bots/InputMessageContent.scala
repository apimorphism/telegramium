package telegramium.bots

sealed trait InputMessageContent {}

/** Represents the content of a venue message to be sent as the result of an inline
  * query.
  *
  * @param latitude Latitude of the venue in degrees
  * @param longitude Longitude of the venue in degrees
  * @param title Name of the venue
  * @param address Address of the venue
  * @param foursquareId Optional. Foursquare identifier of the venue, if known
  * @param foursquareType Optional. Foursquare type of the venue, if known. (For
  * example, “arts_entertainment/default”,
  * “arts_entertainment/aquarium” or “food/icecream”.)
  * @param googlePlaceId Optional. Google Places identifier of the venue
  * @param googlePlaceType Optional. Google Places type of the venue. (See supported
  * types.) */
final case class InputVenueMessageContent(latitude: Float,
                                          longitude: Float,
                                          title: String,
                                          address: String,
                                          foursquareId: Option[String] = Option.empty,
                                          foursquareType: Option[String] = Option.empty,
                                          googlePlaceId: Option[String] = Option.empty,
                                          googlePlaceType: Option[String] = Option.empty)
    extends InputMessageContent

/** Represents the content of a contact message to be sent as the result of an
  * inline query.
  *
  * @param phoneNumber Contact's phone number
  * @param firstName Contact's first name
  * @param lastName Optional. Contact's last name
  * @param vcard Optional. Additional data about the contact in the form of
  * a vCard, 0-2048 bytes */
final case class InputContactMessageContent(phoneNumber: String,
                                            firstName: String,
                                            lastName: Option[String] = Option.empty,
                                            vcard: Option[String] = Option.empty)
    extends InputMessageContent

/** Represents the content of a location message to be sent as the result of an
  * inline query.
  *
  * @param latitude Latitude of the location in degrees
  * @param longitude Longitude of the location in degrees
  * @param horizontalAccuracy Optional. The radius of uncertainty for the location,
  * measured in meters; 0-1500
  * @param livePeriod Optional. Period in seconds for which the location can be
  * updated, should be between 60 and 86400.
  * @param heading Optional. For live locations, a direction in which the user
  * is moving, in degrees. Must be between 1 and 360 if
  * specified.
  * @param proximityAlertRadius Optional. For live locations, a maximum distance for
  * proximity alerts about approaching another chat member, in
  * meters. Must be between 1 and 100000 if specified. */
final case class InputLocationMessageContent(latitude: Float,
                                             longitude: Float,
                                             horizontalAccuracy: Option[Float] = Option.empty,
                                             livePeriod: Option[Int] = Option.empty,
                                             heading: Option[Int] = Option.empty,
                                             proximityAlertRadius: Option[Int] = Option.empty)
    extends InputMessageContent

/** Represents the content of a text message to be sent as the result of an inline
  * query.
  *
  * @param messageText Text of the message to be sent, 1-4096 characters
  * @param parseMode Optional. Mode for parsing entities in the message text.
  * See formatting options for more details.
  * @param entities Optional. List of special entities that appear in message
  * text, which can be specified instead of parse_mode
  * @param disableWebPagePreview Optional. Disables link previews for links in the sent
  * message */
final case class InputTextMessageContent(messageText: String,
                                         parseMode: Option[ParseMode] = Option.empty,
                                         entities: List[MessageEntity] = List.empty,
                                         disableWebPagePreview: Option[Boolean] = Option.empty)
    extends InputMessageContent
