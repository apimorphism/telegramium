package telegramium.bots

sealed trait InlineQueryResult {}

/** Represents a link to an animated GIF file. By default, this animated GIF file will be sent by the user with optional
  * caption. Alternatively, you can use <em>input_message_content</em> to send a message with the specified content
  * instead of the animation.
  *
  * @param id
  *   Unique identifier for this result, 1-64 bytes
  * @param gifUrl
  *   A valid URL for the GIF file
  * @param thumbnailUrl
  *   URL of the static (JPEG or GIF) or animated (MPEG4) thumbnail for the result
  * @param gifWidth
  *   <em>Optional</em>. Width of the GIF
  * @param gifHeight
  *   <em>Optional</em>. Height of the GIF
  * @param gifDuration
  *   <em>Optional</em>. Duration of the GIF in seconds
  * @param thumbnailMimeType
  *   <em>Optional</em>. MIME type of the thumbnail, must be one of “image/jpeg”, “image/gif”, or “video/mp4”. Defaults
  *   to “image/jpeg”.
  * @param title
  *   <em>Optional</em>. Title for the result
  * @param caption
  *   <em>Optional</em>. Caption of the GIF file to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param showCaptionAboveMedia
  *   <em>Optional</em>. Pass <em>True</em> if the caption must be shown above the message media
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the GIF animation
  */
final case class InlineQueryResultGif(
  id: String,
  gifUrl: String,
  thumbnailUrl: String,
  gifWidth: Option[Int] = Option.empty,
  gifHeight: Option[Int] = Option.empty,
  gifDuration: Option[Int] = Option.empty,
  thumbnailMimeType: Option[String] = Option.empty,
  title: Option[String] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty
) extends InlineQueryResult

/** Represents a venue. By default, the venue will be sent by the user. Alternatively, you can use
  * <em>input_message_content</em> to send a message with the specified content instead of the venue.
  *
  * @param id
  *   Unique identifier for this result, 1-64 Bytes
  * @param latitude
  *   Latitude of the venue location in degrees
  * @param longitude
  *   Longitude of the venue location in degrees
  * @param title
  *   Title of the venue
  * @param address
  *   Address of the venue
  * @param foursquareId
  *   <em>Optional</em>. Foursquare identifier of the venue if known
  * @param foursquareType
  *   <em>Optional</em>. Foursquare type of the venue, if known. (For example, “arts_entertainment/default”,
  *   “arts_entertainment/aquarium” or “food/icecream”.)
  * @param googlePlaceId
  *   <em>Optional</em>. Google Places identifier of the venue
  * @param googlePlaceType
  *   <em>Optional</em>. Google Places type of the venue. (See <a
  *   href="https://developers.google.com/places/web-service/supported_types">supported types</a>.)
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the venue
  * @param thumbnailUrl
  *   <em>Optional</em>. Url of the thumbnail for the result
  * @param thumbnailWidth
  *   <em>Optional</em>. Thumbnail width
  * @param thumbnailHeight
  *   <em>Optional</em>. Thumbnail height
  */
final case class InlineQueryResultVenue(
  id: String,
  latitude: Float,
  longitude: Float,
  title: String,
  address: String,
  foursquareId: Option[String] = Option.empty,
  foursquareType: Option[String] = Option.empty,
  googlePlaceId: Option[String] = Option.empty,
  googlePlaceType: Option[String] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty,
  thumbnailUrl: Option[String] = Option.empty,
  thumbnailWidth: Option[Int] = Option.empty,
  thumbnailHeight: Option[Int] = Option.empty
) extends InlineQueryResult

/** Represents a contact with a phone number. By default, this contact will be sent by the user. Alternatively, you can
  * use <em>input_message_content</em> to send a message with the specified content instead of the contact.
  *
  * @param id
  *   Unique identifier for this result, 1-64 Bytes
  * @param phoneNumber
  *   Contact's phone number
  * @param firstName
  *   Contact's first name
  * @param lastName
  *   <em>Optional</em>. Contact's last name
  * @param vcard
  *   <em>Optional</em>. Additional data about the contact in the form of a <a
  *   href="https://en.wikipedia.org/wiki/VCard">vCard</a>, 0-2048 bytes
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the contact
  * @param thumbnailUrl
  *   <em>Optional</em>. Url of the thumbnail for the result
  * @param thumbnailWidth
  *   <em>Optional</em>. Thumbnail width
  * @param thumbnailHeight
  *   <em>Optional</em>. Thumbnail height
  */
final case class InlineQueryResultContact(
  id: String,
  phoneNumber: String,
  firstName: String,
  lastName: Option[String] = Option.empty,
  vcard: Option[String] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty,
  thumbnailUrl: Option[String] = Option.empty,
  thumbnailWidth: Option[Int] = Option.empty,
  thumbnailHeight: Option[Int] = Option.empty
) extends InlineQueryResult

/** Represents a link to a photo. By default, this photo will be sent by the user with optional caption. Alternatively,
  * you can use <em>input_message_content</em> to send a message with the specified content instead of the photo.
  *
  * @param id
  *   Unique identifier for this result, 1-64 bytes
  * @param photoUrl
  *   A valid URL of the photo. Photo must be in <strong>JPEG</strong> format. Photo size must not exceed 5MB.
  * @param thumbnailUrl
  *   URL of the thumbnail for the photo
  * @param photoWidth
  *   <em>Optional</em>. Width of the photo
  * @param photoHeight
  *   <em>Optional</em>. Height of the photo
  * @param title
  *   <em>Optional</em>. Title for the result
  * @param description
  *   <em>Optional</em>. Short description of the result
  * @param caption
  *   <em>Optional</em>. Caption of the photo to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the photo caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param showCaptionAboveMedia
  *   <em>Optional</em>. Pass <em>True</em> if the caption must be shown above the message media
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the photo
  */
final case class InlineQueryResultPhoto(
  id: String,
  photoUrl: String,
  thumbnailUrl: String,
  photoWidth: Option[Int] = Option.empty,
  photoHeight: Option[Int] = Option.empty,
  title: Option[String] = Option.empty,
  description: Option[String] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty
) extends InlineQueryResult

/** Represents a link to a file. By default, this file will be sent by the user with an optional caption. Alternatively,
  * you can use <em>input_message_content</em> to send a message with the specified content instead of the file.
  * Currently, only <strong>.PDF</strong> and <strong>.ZIP</strong> files can be sent using this method.
  *
  * @param id
  *   Unique identifier for this result, 1-64 bytes
  * @param title
  *   Title for the result
  * @param documentUrl
  *   A valid URL for the file
  * @param mimeType
  *   MIME type of the content of the file, either “application/pdf” or “application/zip”
  * @param caption
  *   <em>Optional</em>. Caption of the document to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the document caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param description
  *   <em>Optional</em>. Short description of the result
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the file
  * @param thumbnailUrl
  *   <em>Optional</em>. URL of the thumbnail (JPEG only) for the file
  * @param thumbnailWidth
  *   <em>Optional</em>. Thumbnail width
  * @param thumbnailHeight
  *   <em>Optional</em>. Thumbnail height
  */
final case class InlineQueryResultDocument(
  id: String,
  title: String,
  documentUrl: String,
  mimeType: String,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  description: Option[String] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty,
  thumbnailUrl: Option[String] = Option.empty,
  thumbnailWidth: Option[Int] = Option.empty,
  thumbnailHeight: Option[Int] = Option.empty
) extends InlineQueryResult

/** Represents a link to a voice message stored on the Telegram servers. By default, this voice message will be sent by
  * the user. Alternatively, you can use <em>input_message_content</em> to send a message with the specified content
  * instead of the voice message.
  *
  * @param id
  *   Unique identifier for this result, 1-64 bytes
  * @param voiceFileId
  *   A valid file identifier for the voice message
  * @param title
  *   Voice message title
  * @param caption
  *   <em>Optional</em>. Caption, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the voice message caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the voice message
  */
final case class InlineQueryResultCachedVoice(
  id: String,
  voiceFileId: String,
  title: String,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty
) extends InlineQueryResult

/** Represents a link to an article or web page.
  *
  * @param id
  *   Unique identifier for this result, 1-64 Bytes
  * @param title
  *   Title of the result
  * @param inputMessageContent
  *   Content of the message to be sent
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param url
  *   <em>Optional</em>. URL of the result
  * @param description
  *   <em>Optional</em>. Short description of the result
  * @param thumbnailUrl
  *   <em>Optional</em>. Url of the thumbnail for the result
  * @param thumbnailWidth
  *   <em>Optional</em>. Thumbnail width
  * @param thumbnailHeight
  *   <em>Optional</em>. Thumbnail height
  */
final case class InlineQueryResultArticle(
  id: String,
  title: String,
  inputMessageContent: InputMessageContent,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  url: Option[String] = Option.empty,
  description: Option[String] = Option.empty,
  thumbnailUrl: Option[String] = Option.empty,
  thumbnailWidth: Option[Int] = Option.empty,
  thumbnailHeight: Option[Int] = Option.empty
) extends InlineQueryResult

/** Represents a link to an MP3 audio file. By default, this audio file will be sent by the user. Alternatively, you can
  * use <em>input_message_content</em> to send a message with the specified content instead of the audio.
  *
  * @param id
  *   Unique identifier for this result, 1-64 bytes
  * @param audioUrl
  *   A valid URL for the audio file
  * @param title
  *   Title
  * @param caption
  *   <em>Optional</em>. Caption, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the audio caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param performer
  *   <em>Optional</em>. Performer
  * @param audioDuration
  *   <em>Optional</em>. Audio duration in seconds
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the audio
  */
final case class InlineQueryResultAudio(
  id: String,
  audioUrl: String,
  title: String,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  performer: Option[String] = Option.empty,
  audioDuration: Option[Int] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty
) extends InlineQueryResult

/** Represents a link to a video animation (H.264/MPEG-4 AVC video without sound). By default, this animated MPEG-4 file
  * will be sent by the user with optional caption. Alternatively, you can use <em>input_message_content</em> to send a
  * message with the specified content instead of the animation.
  *
  * @param id
  *   Unique identifier for this result, 1-64 bytes
  * @param mpeg4Url
  *   A valid URL for the MPEG4 file
  * @param thumbnailUrl
  *   URL of the static (JPEG or GIF) or animated (MPEG4) thumbnail for the result
  * @param mpeg4Width
  *   <em>Optional</em>. Video width
  * @param mpeg4Height
  *   <em>Optional</em>. Video height
  * @param mpeg4Duration
  *   <em>Optional</em>. Video duration in seconds
  * @param thumbnailMimeType
  *   <em>Optional</em>. MIME type of the thumbnail, must be one of “image/jpeg”, “image/gif”, or “video/mp4”. Defaults
  *   to “image/jpeg”.
  * @param title
  *   <em>Optional</em>. Title for the result
  * @param caption
  *   <em>Optional</em>. Caption of the MPEG-4 file to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param showCaptionAboveMedia
  *   <em>Optional</em>. Pass <em>True</em> if the caption must be shown above the message media
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the video animation
  */
final case class InlineQueryResultMpeg4Gif(
  id: String,
  mpeg4Url: String,
  thumbnailUrl: String,
  mpeg4Width: Option[Int] = Option.empty,
  mpeg4Height: Option[Int] = Option.empty,
  mpeg4Duration: Option[Int] = Option.empty,
  thumbnailMimeType: Option[String] = Option.empty,
  title: Option[String] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty
) extends InlineQueryResult

/** Represents a link to a video animation (H.264/MPEG-4 AVC video without sound) stored on the Telegram servers. By
  * default, this animated MPEG-4 file will be sent by the user with an optional caption. Alternatively, you can use
  * <em>input_message_content</em> to send a message with the specified content instead of the animation.
  *
  * @param id
  *   Unique identifier for this result, 1-64 bytes
  * @param mpeg4FileId
  *   A valid file identifier for the MPEG4 file
  * @param title
  *   <em>Optional</em>. Title for the result
  * @param caption
  *   <em>Optional</em>. Caption of the MPEG-4 file to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param showCaptionAboveMedia
  *   <em>Optional</em>. Pass <em>True</em> if the caption must be shown above the message media
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the video animation
  */
final case class InlineQueryResultCachedMpeg4Gif(
  id: String,
  mpeg4FileId: String,
  title: Option[String] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty
) extends InlineQueryResult

/** Represents a link to a file stored on the Telegram servers. By default, this file will be sent by the user with an
  * optional caption. Alternatively, you can use <em>input_message_content</em> to send a message with the specified
  * content instead of the file.
  *
  * @param id
  *   Unique identifier for this result, 1-64 bytes
  * @param title
  *   Title for the result
  * @param documentFileId
  *   A valid file identifier for the file
  * @param description
  *   <em>Optional</em>. Short description of the result
  * @param caption
  *   <em>Optional</em>. Caption of the document to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the document caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the file
  */
final case class InlineQueryResultCachedDocument(
  id: String,
  title: String,
  documentFileId: String,
  description: Option[String] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty
) extends InlineQueryResult

/** Represents a link to a video file stored on the Telegram servers. By default, this video file will be sent by the
  * user with an optional caption. Alternatively, you can use <em>input_message_content</em> to send a message with the
  * specified content instead of the video.
  *
  * @param id
  *   Unique identifier for this result, 1-64 bytes
  * @param videoFileId
  *   A valid file identifier for the video file
  * @param title
  *   Title for the result
  * @param description
  *   <em>Optional</em>. Short description of the result
  * @param caption
  *   <em>Optional</em>. Caption of the video to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the video caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param showCaptionAboveMedia
  *   <em>Optional</em>. Pass <em>True</em> if the caption must be shown above the message media
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the video
  */
final case class InlineQueryResultCachedVideo(
  id: String,
  videoFileId: String,
  title: String,
  description: Option[String] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty
) extends InlineQueryResult

/** Represents a <a href="https://core.telegram.org/bots/api#game">Game</a>.
  *
  * @param id
  *   Unique identifier for this result, 1-64 bytes
  * @param gameShortName
  *   Short name of the game
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  */
final case class InlineQueryResultGame(
  id: String,
  gameShortName: String,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty
) extends InlineQueryResult

/** Represents a link to a photo stored on the Telegram servers. By default, this photo will be sent by the user with an
  * optional caption. Alternatively, you can use <em>input_message_content</em> to send a message with the specified
  * content instead of the photo.
  *
  * @param id
  *   Unique identifier for this result, 1-64 bytes
  * @param photoFileId
  *   A valid file identifier of the photo
  * @param title
  *   <em>Optional</em>. Title for the result
  * @param description
  *   <em>Optional</em>. Short description of the result
  * @param caption
  *   <em>Optional</em>. Caption of the photo to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the photo caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param showCaptionAboveMedia
  *   <em>Optional</em>. Pass <em>True</em> if the caption must be shown above the message media
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the photo
  */
final case class InlineQueryResultCachedPhoto(
  id: String,
  photoFileId: String,
  title: Option[String] = Option.empty,
  description: Option[String] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty
) extends InlineQueryResult

/** Represents a link to a sticker stored on the Telegram servers. By default, this sticker will be sent by the user.
  * Alternatively, you can use <em>input_message_content</em> to send a message with the specified content instead of
  * the sticker.
  *
  * @param id
  *   Unique identifier for this result, 1-64 bytes
  * @param stickerFileId
  *   A valid file identifier of the sticker
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the sticker
  */
final case class InlineQueryResultCachedSticker(
  id: String,
  stickerFileId: String,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty
) extends InlineQueryResult

/** Represents a link to a page containing an embedded video player or a video file. By default, this video file will be
  * sent by the user with an optional caption. Alternatively, you can use <em>input_message_content</em> to send a
  * message with the specified content instead of the video.
  *
  * @param id
  *   Unique identifier for this result, 1-64 bytes
  * @param videoUrl
  *   A valid URL for the embedded video player or video file
  * @param mimeType
  *   MIME type of the content of the video URL, “text/html” or “video/mp4”
  * @param thumbnailUrl
  *   URL of the thumbnail (JPEG only) for the video
  * @param title
  *   Title for the result
  * @param caption
  *   <em>Optional</em>. Caption of the video to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the video caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param showCaptionAboveMedia
  *   <em>Optional</em>. Pass <em>True</em> if the caption must be shown above the message media
  * @param videoWidth
  *   <em>Optional</em>. Video width
  * @param videoHeight
  *   <em>Optional</em>. Video height
  * @param videoDuration
  *   <em>Optional</em>. Video duration in seconds
  * @param description
  *   <em>Optional</em>. Short description of the result
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the video. This field is <strong>required</strong>
  *   if InlineQueryResultVideo is used to send an HTML-page as a result (e.g., a YouTube video).
  */
final case class InlineQueryResultVideo(
  id: String,
  videoUrl: String,
  mimeType: String,
  thumbnailUrl: String,
  title: String,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  videoWidth: Option[Int] = Option.empty,
  videoHeight: Option[Int] = Option.empty,
  videoDuration: Option[Int] = Option.empty,
  description: Option[String] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty
) extends InlineQueryResult

/** Represents a link to an MP3 audio file stored on the Telegram servers. By default, this audio file will be sent by
  * the user. Alternatively, you can use <em>input_message_content</em> to send a message with the specified content
  * instead of the audio.
  *
  * @param id
  *   Unique identifier for this result, 1-64 bytes
  * @param audioFileId
  *   A valid file identifier for the audio file
  * @param caption
  *   <em>Optional</em>. Caption, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the audio caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the audio
  */
final case class InlineQueryResultCachedAudio(
  id: String,
  audioFileId: String,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty
) extends InlineQueryResult

/** Represents a link to an animated GIF file stored on the Telegram servers. By default, this animated GIF file will be
  * sent by the user with an optional caption. Alternatively, you can use <em>input_message_content</em> to send a
  * message with specified content instead of the animation.
  *
  * @param id
  *   Unique identifier for this result, 1-64 bytes
  * @param gifFileId
  *   A valid file identifier for the GIF file
  * @param title
  *   <em>Optional</em>. Title for the result
  * @param caption
  *   <em>Optional</em>. Caption of the GIF file to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param showCaptionAboveMedia
  *   <em>Optional</em>. Pass <em>True</em> if the caption must be shown above the message media
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the GIF animation
  */
final case class InlineQueryResultCachedGif(
  id: String,
  gifFileId: String,
  title: Option[String] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty
) extends InlineQueryResult

/** Represents a location on a map. By default, the location will be sent by the user. Alternatively, you can use
  * <em>input_message_content</em> to send a message with the specified content instead of the location.
  *
  * @param id
  *   Unique identifier for this result, 1-64 Bytes
  * @param latitude
  *   Location latitude in degrees
  * @param longitude
  *   Location longitude in degrees
  * @param title
  *   Location title
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
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the location
  * @param thumbnailUrl
  *   <em>Optional</em>. Url of the thumbnail for the result
  * @param thumbnailWidth
  *   <em>Optional</em>. Thumbnail width
  * @param thumbnailHeight
  *   <em>Optional</em>. Thumbnail height
  */
final case class InlineQueryResultLocation(
  id: String,
  latitude: Float,
  longitude: Float,
  title: String,
  horizontalAccuracy: Option[Float] = Option.empty,
  livePeriod: Option[Int] = Option.empty,
  heading: Option[Int] = Option.empty,
  proximityAlertRadius: Option[Int] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty,
  thumbnailUrl: Option[String] = Option.empty,
  thumbnailWidth: Option[Int] = Option.empty,
  thumbnailHeight: Option[Int] = Option.empty
) extends InlineQueryResult

/** Represents a link to a voice recording in an .OGG container encoded with OPUS. By default, this voice recording will
  * be sent by the user. Alternatively, you can use <em>input_message_content</em> to send a message with the specified
  * content instead of the the voice message.
  *
  * @param id
  *   Unique identifier for this result, 1-64 bytes
  * @param voiceUrl
  *   A valid URL for the voice recording
  * @param title
  *   Recording title
  * @param caption
  *   <em>Optional</em>. Caption, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the voice message caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param voiceDuration
  *   <em>Optional</em>. Recording duration in seconds
  * @param replyMarkup
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#inline-keyboards">Inline keyboard</a> attached
  *   to the message
  * @param inputMessageContent
  *   <em>Optional</em>. Content of the message to be sent instead of the voice recording
  */
final case class InlineQueryResultVoice(
  id: String,
  voiceUrl: String,
  title: String,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  voiceDuration: Option[Int] = Option.empty,
  replyMarkup: Option[InlineKeyboardMarkup] = Option.empty,
  inputMessageContent: Option[InputMessageContent] = Option.empty
) extends InlineQueryResult
