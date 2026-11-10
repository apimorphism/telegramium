package telegramium.bots

sealed trait InputMessageContent {}

/** Represents the <a href="https://core.telegram.org/bots/api#inputmessagecontent">content</a> of a venue message to be
  * sent as the result of an inline query.
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
  *   <em>Optional</em>. Foursquare identifier of the venue, if known
  * @param foursquareType
  *   <em>Optional</em>. Foursquare type of the venue, if known. (For example, “arts_entertainment/default”,
  *   “arts_entertainment/aquarium” or “food/icecream”.)
  * @param googlePlaceId
  *   <em>Optional</em>. Google Places identifier of the venue
  * @param googlePlaceType
  *   <em>Optional</em>. Google Places type of the venue. (See <a
  *   href="https://developers.google.com/places/web-service/supported_types">supported types</a>.)
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

/** Represents the <a href="https://core.telegram.org/bots/api#inputmessagecontent">content</a> of an invoice message to
  * be sent as the result of an inline query.
  *
  * @param title
  *   Product name, 1-32 characters
  * @param description
  *   Product description, 1-255 characters
  * @param payload
  *   Bot-defined invoice payload, 1-128 bytes. This will not be displayed to the user, use it for your internal
  *   processes.
  * @param currency
  *   Three-letter ISO 4217 currency code, see <a
  *   href="https://core.telegram.org/bots/payments#supported-currencies">more on currencies</a>. Pass “XTR” for
  *   payments in <a href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param providerToken
  *   <em>Optional</em>. Payment provider token, obtained via <a href="https://t.me/botfather">&#064;BotFather</a>. Pass
  *   an empty string for payments in <a href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param prices
  *   Price breakdown, a JSON-serialized list of components (e.g. product price, tax, discount, delivery cost, delivery
  *   tax, bonus, etc.). Must contain exactly one item for payments in <a href="https://t.me/BotNews/90">Telegram
  *   Stars</a>.
  * @param maxTipAmount
  *   <em>Optional</em>. The maximum accepted amount for tips in the <em>smallest units</em> of the currency (integer,
  *   <strong>not</strong> float/double). For example, for a maximum tip of <code>US$ 1.45</code> pass
  *   <code>max_tip_amount = 145</code>. See the <em>exp</em> parameter in <a
  *   href="https://core.telegram.org/bots/payments/currencies.json">currencies.json</a>, it shows the number of digits
  *   past the decimal point for each currency (2 for the majority of currencies). Defaults to 0. Not supported for
  *   payments in <a href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param suggestedTipAmounts
  *   <em>Optional</em>. A JSON-serialized Array of suggested amounts of tip in the <em>smallest units</em> of the
  *   currency (integer, <strong>not</strong> float/double). At most 4 suggested tip amounts can be specified. The
  *   suggested tip amounts must be positive, passed in a strictly increased order and must not exceed
  *   <em>max_tip_amount</em>.
  * @param providerData
  *   <em>Optional</em>. A JSON-serialized object for data about the invoice, which will be shared with the payment
  *   provider. A detailed description of the required fields should be provided by the payment provider.
  * @param photoUrl
  *   <em>Optional</em>. URL of the product photo for the invoice. Can be a photo of the goods or a marketing image for
  *   a service.
  * @param photoSize
  *   <em>Optional</em>. Photo size in bytes
  * @param photoWidth
  *   <em>Optional</em>. Photo width
  * @param photoHeight
  *   <em>Optional</em>. Photo height
  * @param needName
  *   <em>Optional</em>. Pass <em>True</em> if you require the user's full name to complete the order. Ignored for
  *   payments in <a href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param needPhoneNumber
  *   <em>Optional</em>. Pass <em>True</em> if you require the user's phone number to complete the order. Ignored for
  *   payments in <a href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param needEmail
  *   <em>Optional</em>. Pass <em>True</em> if you require the user's email address to complete the order. Ignored for
  *   payments in <a href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param needShippingAddress
  *   <em>Optional</em>. Pass <em>True</em> if you require the user's shipping address to complete the order. Ignored
  *   for payments in <a href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param sendPhoneNumberToProvider
  *   <em>Optional</em>. Pass <em>True</em> if the user's phone number should be sent to the provider. Ignored for
  *   payments in <a href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param sendEmailToProvider
  *   <em>Optional</em>. Pass <em>True</em> if the user's email address should be sent to the provider. Ignored for
  *   payments in <a href="https://t.me/BotNews/90">Telegram Stars</a>.
  * @param isFlexible
  *   <em>Optional</em>. Pass <em>True</em> if the final price depends on the shipping method. Ignored for payments in
  *   <a href="https://t.me/BotNews/90">Telegram Stars</a>.
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

/** Represents the <a href="https://core.telegram.org/bots/api#inputmessagecontent">content</a> of a contact message to
  * be sent as the result of an inline query.
  *
  * @param phoneNumber
  *   Contact's phone number
  * @param firstName
  *   Contact's first name
  * @param lastName
  *   <em>Optional</em>. Contact's last name
  * @param vcard
  *   <em>Optional</em>. Additional data about the contact in the form of a <a
  *   href="https://en.wikipedia.org/wiki/VCard">vCard</a>, 0-2048 bytes
  */
final case class InputContactMessageContent(
  phoneNumber: String,
  firstName: String,
  lastName: Option[String] = Option.empty,
  vcard: Option[String] = Option.empty
) extends InputMessageContent

/** Represents the <a href="https://core.telegram.org/bots/api#inputmessagecontent">content</a> of a location message to
  * be sent as the result of an inline query.
  *
  * @param latitude
  *   Latitude of the location in degrees
  * @param longitude
  *   Longitude of the location in degrees
  * @param horizontalAccuracy
  *   <em>Optional</em>. The radius of uncertainty for the location, measured in meters; 0-1500
  * @param livePeriod
  *   <em>Optional</em>. Period in seconds during which the location can be updated, must be between 60 and 86400, or
  *   0x7FFFFFFF for live locations that can be edited indefinitely
  * @param heading
  *   <em>Optional</em>. For live locations, a direction in which the user is moving, in degrees. Must be between 1 and
  *   360 if specified.
  * @param proximityAlertRadius
  *   <em>Optional</em>. For live locations, a maximum distance for proximity alerts about approaching another chat
  *   member, in meters. Must be between 1 and 100000 if specified.
  */
final case class InputLocationMessageContent(
  latitude: Float,
  longitude: Float,
  horizontalAccuracy: Option[Float] = Option.empty,
  livePeriod: Option[Int] = Option.empty,
  heading: Option[Int] = Option.empty,
  proximityAlertRadius: Option[Int] = Option.empty
) extends InputMessageContent

/** Represents the <a href="https://core.telegram.org/bots/api#inputmessagecontent">content</a> of a text message to be
  * sent as the result of an inline query.
  *
  * @param messageText
  *   Text of the message to be sent, 1-4096 characters
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the message text. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param entities
  *   <em>Optional</em>. List of special entities that appear in message text, which can be specified instead of
  *   <em>parse_mode</em>
  * @param linkPreviewOptions
  *   <em>Optional</em>. Link preview generation options for the message
  */
final case class InputTextMessageContent(
  messageText: String,
  parseMode: Option[ParseMode] = Option.empty,
  entities: List[MessageEntity] = List.empty,
  linkPreviewOptions: Option[LinkPreviewOptions] = Option.empty
) extends InputMessageContent

/** Represents the <a href="https://core.telegram.org/bots/api#inputmessagecontent">content</a> of a message with an <a
  * href="https://core.telegram.org/bots/widgets">HTML widget</a> to be sent as the result of an inline query.
  * Currently, widgets can't be sent in response to inline queries in the production environment.
  *
  * @param html
  *   HTML code of the widget, 1-32768 bytes. The code must not exceed 4096 bytes when compressed by zlib with the
  *   maximum compression level.
  * @param width
  *   <em>Optional</em>. Width of the widget in logical pixels; omit if the widget must occupy the whole width of the
  *   message bubble. The width is clamped by the app to the range from 200 to the maximum width of the message bubble.
  * @param height
  *   <em>Optional</em>. Height of the widget in logical pixels; omit if the height of the widget isn't fixed. The
  *   height is clamped by the app to the range 50-600.
  * @param publicData
  *   <em>Optional</em>. Public data of the widget, 0-1024 bytes, which will be available wherever the widget is shown.
  *   If the widget is shared, then all messages with it will have the same public data.
  * @param chatData
  *   <em>Optional</em>. Data of the widget, which is specific to the message, 0-1024 bytes
  * @param caption
  *   <em>Optional</em>. Widget caption, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the widget caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param showCaptionAboveMedia
  *   <em>Optional</em>. Pass <em>True</em> if the caption must be shown above the widget
  * @param allowQueries
  *   <em>Optional</em>. Pass <em>True</em> to allow the widget to send queries to the bot
  * @param isShared
  *   <em>Optional</em>. Pass <em>True</em> to create a shared widget. Each message sent from the result creates a
  *   separate shared widget, whose identifier is received by the bot only in the field <em>widget_id</em> of queries
  *   from the widget. To send an existing shared widget in response to an inline query, use <a
  *   href="https://core.telegram.org/bots/api#inputsharedwidgetmessagecontent">InputSharedWidgetMessageContent</a>
  *   instead.
  */
final case class InputWidgetMessageContent(
  html: String,
  width: Option[Int] = Option.empty,
  height: Option[Int] = Option.empty,
  publicData: Option[String] = Option.empty,
  chatData: Option[String] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  allowQueries: Option[Boolean] = Option.empty,
  isShared: Option[Boolean] = Option.empty
) extends InputMessageContent

/** Represents the <a href="https://core.telegram.org/bots/api#inputmessagecontent">content</a> of a rich message to be
  * sent as the result of an inline query.
  *
  * @param richMessage
  *   The message to be sent. Only previously uploaded files may be used in the message.
  */
final case class InputRichMessageContent(richMessage: InputRichMessage) extends InputMessageContent

/** Represents the <a href="https://core.telegram.org/bots/api#inputmessagecontent">content</a> of a message with a
  * shared widget to be sent as the result of an inline query. Currently, widgets can't be sent in response to inline
  * queries in the production environment.
  *
  * @param widgetId
  *   Unique identifier of the shared widget created by the bot
  * @param chatData
  *   <em>Optional</em>. Data of the widget, which is specific to the message, 0-1024 bytes
  * @param caption
  *   <em>Optional</em>. Widget caption, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the widget caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param showCaptionAboveMedia
  *   <em>Optional</em>. Pass <em>True</em> if the caption must be shown above the widget
  */
final case class InputSharedWidgetMessageContent(
  widgetId: String,
  chatData: Option[String] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty
) extends InputMessageContent
