package telegramium.bots

sealed trait InputMessageContent {}

/** Represents the content of a venue message to be sent as the result of an inline query.
  *
  * @param latitude
  *   Latitude of the venue in degrees
  * @param longitude
  *   Longitude of the venue in degrees
  * @param title
  *   Name of the venue
  * @param address
  *   Address of the venue
  * @param foursquareId
  *   Optional. Foursquare identifier of the venue, if known
  * @param foursquareType
  *   Optional. Foursquare type of the venue, if known. (For example, “arts_entertainment/default”,
  *   “arts_entertainment/aquarium” or “food/icecream”.)
  * @param googlePlaceId
  *   Optional. Google Places identifier of the venue
  * @param googlePlaceType
  *   Optional. Google Places type of the venue. (See supported types.)
  */
final case class InputVenueMessageContent(
  latitude: Float,
  longitude: Float,
  title: String,
  address: String,
  foursquareId: Option[String] = Option.empty,
  foursquareType: Option[String] = Option.empty,
  googlePlaceId: Option[String] = Option.empty,
  googlePlaceType: Option[String] = Option.empty
) extends InputMessageContent

/** Represents the content of an invoice message to be sent as the result of an inline query.
  *
  * @param title
  *   Product name, 1-32 characters
  * @param description
  *   Product description, 1-255 characters
  * @param payload
  *   Bot-defined invoice payload, 1-128 bytes. This will not be displayed to the user, use it for your internal
  *   processes.
  * @param currency
  *   Three-letter ISO 4217 currency code, see more on currencies. Pass “XTR” for payments in Telegram Stars.
  * @param providerToken
  *   Optional. Payment provider token, obtained via &#064;BotFather. Pass an empty string for payments in Telegram
  *   Stars.
  * @param prices
  *   Price breakdown, a JSON-serialized list of components (e.g. product price, tax, discount, delivery cost, delivery
  *   tax, bonus, etc.). Must contain exactly one item for payments in Telegram Stars.
  * @param maxTipAmount
  *   Optional. The maximum accepted amount for tips in the smallest units of the currency (integer, not float/double).
  *   For example, for a maximum tip of US$ 1.45 pass max_tip_amount = 145. See the exp parameter in currencies.json, it
  *   shows the number of digits past the decimal point for each currency (2 for the majority of currencies). Defaults
  *   to 0. Not supported for payments in Telegram Stars.
  * @param suggestedTipAmounts
  *   Optional. A JSON-serialized array of suggested amounts of tip in the smallest units of the currency (integer, not
  *   float/double). At most 4 suggested tip amounts can be specified. The suggested tip amounts must be positive,
  *   passed in a strictly increased order and must not exceed max_tip_amount.
  * @param providerData
  *   Optional. A JSON-serialized object for data about the invoice, which will be shared with the payment provider. A
  *   detailed description of the required fields should be provided by the payment provider.
  * @param photoUrl
  *   Optional. URL of the product photo for the invoice. Can be a photo of the goods or a marketing image for a
  *   service.
  * @param photoSize
  *   Optional. Photo size in bytes
  * @param photoWidth
  *   Optional. Photo width
  * @param photoHeight
  *   Optional. Photo height
  * @param needName
  *   Optional. Pass True if you require the user's full name to complete the order. Ignored for payments in Telegram
  *   Stars.
  * @param needPhoneNumber
  *   Optional. Pass True if you require the user's phone number to complete the order. Ignored for payments in Telegram
  *   Stars.
  * @param needEmail
  *   Optional. Pass True if you require the user's email address to complete the order. Ignored for payments in
  *   Telegram Stars.
  * @param needShippingAddress
  *   Optional. Pass True if you require the user's shipping address to complete the order. Ignored for payments in
  *   Telegram Stars.
  * @param sendPhoneNumberToProvider
  *   Optional. Pass True if the user's phone number should be sent to the provider. Ignored for payments in Telegram
  *   Stars.
  * @param sendEmailToProvider
  *   Optional. Pass True if the user's email address should be sent to the provider. Ignored for payments in Telegram
  *   Stars.
  * @param isFlexible
  *   Optional. Pass True if the final price depends on the shipping method. Ignored for payments in Telegram Stars.
  */
final case class InputInvoiceMessageContent(
  title: String,
  description: String,
  payload: String,
  currency: String,
  providerToken: Option[String] = Option.empty,
  prices: List[LabeledPrice] = List.empty,
  maxTipAmount: Option[Long] = Option.empty,
  suggestedTipAmounts: List[Long] = List.empty,
  providerData: Option[String] = Option.empty,
  photoUrl: Option[String] = Option.empty,
  photoSize: Option[Long] = Option.empty,
  photoWidth: Option[Int] = Option.empty,
  photoHeight: Option[Int] = Option.empty,
  needName: Option[Boolean] = Option.empty,
  needPhoneNumber: Option[Boolean] = Option.empty,
  needEmail: Option[Boolean] = Option.empty,
  needShippingAddress: Option[Boolean] = Option.empty,
  sendPhoneNumberToProvider: Option[Boolean] = Option.empty,
  sendEmailToProvider: Option[Boolean] = Option.empty,
  isFlexible: Option[Boolean] = Option.empty
) extends InputMessageContent

/** Represents the content of a contact message to be sent as the result of an inline query.
  *
  * @param phoneNumber
  *   Contact's phone number
  * @param firstName
  *   Contact's first name
  * @param lastName
  *   Optional. Contact's last name
  * @param vcard
  *   Optional. Additional data about the contact in the form of a vCard, 0-2048 bytes
  */
final case class InputContactMessageContent(
  phoneNumber: String,
  firstName: String,
  lastName: Option[String] = Option.empty,
  vcard: Option[String] = Option.empty
) extends InputMessageContent

/** Represents the content of a location message to be sent as the result of an inline query.
  *
  * @param latitude
  *   Latitude of the location in degrees
  * @param longitude
  *   Longitude of the location in degrees
  * @param horizontalAccuracy
  *   Optional. The radius of uncertainty for the location, measured in meters; 0-1500
  * @param livePeriod
  *   Optional. Period in seconds during which the location can be updated, should be between 60 and 86400, or
  *   0x7FFFFFFF for live locations that can be edited indefinitely.
  * @param heading
  *   Optional. For live locations, a direction in which the user is moving, in degrees. Must be between 1 and 360 if
  *   specified.
  * @param proximityAlertRadius
  *   Optional. For live locations, a maximum distance for proximity alerts about approaching another chat member, in
  *   meters. Must be between 1 and 100000 if specified.
  */
final case class InputLocationMessageContent(
  latitude: Float,
  longitude: Float,
  horizontalAccuracy: Option[Float] = Option.empty,
  livePeriod: Option[Int] = Option.empty,
  heading: Option[Int] = Option.empty,
  proximityAlertRadius: Option[Int] = Option.empty
) extends InputMessageContent

/** Represents the content of a text message to be sent as the result of an inline query.
  *
  * @param messageText
  *   Text of the message to be sent, 1-4096 characters
  * @param parseMode
  *   Optional. Mode for parsing entities in the message text. See formatting options for more details.
  * @param entities
  *   Optional. List of special entities that appear in message text, which can be specified instead of parse_mode
  * @param linkPreviewOptions
  *   Optional. Link preview generation options for the message
  */
final case class InputTextMessageContent(
  messageText: String,
  parseMode: Option[ParseMode] = Option.empty,
  entities: List[MessageEntity] = List.empty,
  linkPreviewOptions: Option[LinkPreviewOptions] = Option.empty
) extends InputMessageContent
