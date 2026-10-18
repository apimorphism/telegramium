package telegramium.bots

sealed trait InputMedia {}

/** Represents a location to be sent.
  *
  * @param latitude
  *   Latitude of the location
  * @param longitude
  *   Longitude of the location
  * @param horizontalAccuracy
  *   <em>Optional</em>. The radius of uncertainty for the location, measured in meters; 0-1500
  */
final case class InputMediaLocation(latitude: Float, longitude: Float, horizontalAccuracy: Option[Float] = Option.empty)
    extends InputMedia

/** Represents an animation file (GIF or H.264/MPEG-4 AVC video without sound) to be sent.
  *
  * @param media
  *   File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL
  *   for Telegram to get a file from the Internet, or pass “attach://ᐸfile_attach_nameᐳ” to upload a new one using
  *   multipart/form-data under ᐸfile_attach_nameᐳ name.
  * @param thumbnail
  *   <em>Optional</em>. Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported
  *   server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height
  *   should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused
  *   and can be only uploaded as a new file, so you can pass “attach://ᐸfile_attach_nameᐳ” if the thumbnail was
  *   uploaded using multipart/form-data under ᐸfile_attach_nameᐳ.
  * @param caption
  *   <em>Optional</em>. Caption of the animation to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the animation caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param showCaptionAboveMedia
  *   <em>Optional</em>. Pass <em>True</em> if the caption must be shown above the message media
  * @param width
  *   <em>Optional</em>. Animation width
  * @param height
  *   <em>Optional</em>. Animation height
  * @param duration
  *   <em>Optional</em>. Animation duration in seconds
  * @param hasSpoiler
  *   <em>Optional</em>. Pass <em>True</em> if the animation needs to be covered with a spoiler animation
  */
final case class InputMediaAnimation(
  media: IFile,
  thumbnail: Option[IFile] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  width: Option[Int] = Option.empty,
  height: Option[Int] = Option.empty,
  duration: Option[Int] = Option.empty,
  hasSpoiler: Option[Boolean] = Option.empty
) extends InputMedia

/** Represents an HTTP link to be sent.
  *
  * @param url
  *   HTTP URL of the link
  */
final case class InputMediaLink(url: String) extends InputMedia

/** Represents a voice message file to be sent.
  *
  * @param media
  *   File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL
  *   for Telegram to get a file from the Internet, or pass "attach://ᐸfile_attach_nameᐳ" to upload a new one using
  *   multipart/form-data under ᐸfile_attach_nameᐳ name.
  * @param caption
  *   <em>Optional</em>. Caption of the voice message to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the voice message caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param duration
  *   <em>Optional</em>. Duration of the voice message in seconds
  */
final case class InputMediaVoiceNote(
  media: IFile,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  duration: Option[Int] = Option.empty
) extends InputMedia

/** Represents a photo to be sent.
  *
  * @param media
  *   File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL
  *   for Telegram to get a file from the Internet, or pass “attach://ᐸfile_attach_nameᐳ” to upload a new one using
  *   multipart/form-data under ᐸfile_attach_nameᐳ name.
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
  * @param hasSpoiler
  *   <em>Optional</em>. Pass <em>True</em> if the photo needs to be covered with a spoiler animation
  */
final case class InputMediaPhoto(
  media: IFile,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  hasSpoiler: Option[Boolean] = Option.empty
) extends InputMedia

/** Represents a video to be sent.
  *
  * @param media
  *   File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL
  *   for Telegram to get a file from the Internet, or pass “attach://ᐸfile_attach_nameᐳ” to upload a new one using
  *   multipart/form-data under ᐸfile_attach_nameᐳ name.
  * @param thumbnail
  *   <em>Optional</em>. Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported
  *   server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height
  *   should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused
  *   and can be only uploaded as a new file, so you can pass “attach://ᐸfile_attach_nameᐳ” if the thumbnail was
  *   uploaded using multipart/form-data under ᐸfile_attach_nameᐳ.
  * @param cover
  *   <em>Optional</em>. Cover for the video in the message. Pass a file_id to send a file that exists on the Telegram
  *   servers (recommended), pass an HTTP URL for Telegram to get a file from the Internet, or pass
  *   “attach://ᐸfile_attach_nameᐳ” to upload a new one using multipart/form-data under ᐸfile_attach_nameᐳ name.
  * @param startTimestamp
  *   <em>Optional</em>. Start timestamp for the video in the message
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
  * @param width
  *   <em>Optional</em>. Video width
  * @param height
  *   <em>Optional</em>. Video height
  * @param duration
  *   <em>Optional</em>. Video duration in seconds
  * @param supportsStreaming
  *   <em>Optional</em>. Pass <em>True</em> if the uploaded video is suitable for streaming
  * @param hasSpoiler
  *   <em>Optional</em>. Pass <em>True</em> if the video needs to be covered with a spoiler animation
  */
final case class InputMediaVideo(
  media: IFile,
  thumbnail: Option[IFile] = Option.empty,
  cover: Option[IFile] = Option.empty,
  startTimestamp: Option[Int] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  width: Option[Int] = Option.empty,
  height: Option[Int] = Option.empty,
  duration: Option[Int] = Option.empty,
  supportsStreaming: Option[Boolean] = Option.empty,
  hasSpoiler: Option[Boolean] = Option.empty
) extends InputMedia

/** Represents a live photo to be sent.
  *
  * @param media
  *   Video of the live photo to send. Pass a file_id to send a file that exists on the Telegram servers (recommended)
  *   or pass “attach://ᐸfile_attach_nameᐳ” to upload a new one using multipart/form-data under ᐸfile_attach_nameᐳ name.
  *   Sending live photos by a URL is currently unsupported.
  * @param photo
  *   The static photo to send. Pass a file_id to send a file that exists on the Telegram servers (recommended) or pass
  *   “attach://ᐸfile_attach_nameᐳ” to upload a new one using multipart/form-data under ᐸfile_attach_nameᐳ name. Sending
  *   live photos by a URL is currently unsupported.
  * @param caption
  *   <em>Optional</em>. Caption of the live photo to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the live photo caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param showCaptionAboveMedia
  *   <em>Optional</em>. Pass <em>True</em> if the caption must be shown above the message media
  * @param hasSpoiler
  *   <em>Optional</em>. Pass <em>True</em> if the live photo needs to be covered with a spoiler animation
  */
final case class InputMediaLivePhoto(
  media: IFile,
  photo: IFile,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  showCaptionAboveMedia: Option[Boolean] = Option.empty,
  hasSpoiler: Option[Boolean] = Option.empty
) extends InputMedia

/** Represents a venue to be sent.
  *
  * @param latitude
  *   Latitude of the location
  * @param longitude
  *   Longitude of the location
  * @param title
  *   Name of the venue
  * @param address
  *   Address of the venue
  * @param foursquareId
  *   <em>Optional</em>. Foursquare identifier of the venue
  * @param foursquareType
  *   <em>Optional</em>. Foursquare type of the venue, if known. (For example, “arts_entertainment/default”,
  *   “arts_entertainment/aquarium” or “food/icecream”.)
  * @param googlePlaceId
  *   <em>Optional</em>. Google Places identifier of the venue
  * @param googlePlaceType
  *   <em>Optional</em>. Google Places type of the venue. (See <a
  *   href="https://developers.google.com/places/web-service/supported_types">supported types</a>.)
  */
final case class InputMediaVenue(
  latitude: Float,
  longitude: Float,
  title: String,
  address: String,
  foursquareId: Option[String] = Option.empty,
  foursquareType: Option[String] = Option.empty,
  googlePlaceId: Option[String] = Option.empty,
  googlePlaceType: Option[String] = Option.empty
) extends InputMedia

/** Represents a general file to be sent.
  *
  * @param media
  *   File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL
  *   for Telegram to get a file from the Internet, or pass “attach://ᐸfile_attach_nameᐳ” to upload a new one using
  *   multipart/form-data under ᐸfile_attach_nameᐳ name.
  * @param thumbnail
  *   <em>Optional</em>. Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported
  *   server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height
  *   should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused
  *   and can be only uploaded as a new file, so you can pass “attach://ᐸfile_attach_nameᐳ” if the thumbnail was
  *   uploaded using multipart/form-data under ᐸfile_attach_nameᐳ.
  * @param caption
  *   <em>Optional</em>. Caption of the document to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the document caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param disableContentTypeDetection
  *   <em>Optional</em>. Disables automatic server-side content type detection for files uploaded using
  *   multipart/form-data. Always <em>True</em>, if the document is sent as part of an album.
  */
final case class InputMediaDocument(
  media: IFile,
  thumbnail: Option[IFile] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  disableContentTypeDetection: Option[Boolean] = Option.empty
) extends InputMedia

/** Represents a sticker file to be sent.
  *
  * @param media
  *   File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL
  *   for Telegram to get a .WEBP sticker from the Internet, or pass “attach://ᐸfile_attach_nameᐳ” to upload a new
  *   .WEBP, .TGS, or .WEBM sticker using multipart/form-data under ᐸfile_attach_nameᐳ name.
  * @param emoji
  *   <em>Optional</em>. Emoji associated with the sticker; only for just uploaded stickers
  */
final case class InputMediaSticker(media: IFile, emoji: Option[String] = Option.empty) extends InputMedia

/** Represents an audio file to be treated as music to be sent.
  *
  * @param media
  *   File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL
  *   for Telegram to get a file from the Internet, or pass “attach://ᐸfile_attach_nameᐳ” to upload a new one using
  *   multipart/form-data under ᐸfile_attach_nameᐳ name.
  * @param thumbnail
  *   <em>Optional</em>. Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported
  *   server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height
  *   should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused
  *   and can be only uploaded as a new file, so you can pass “attach://ᐸfile_attach_nameᐳ” if the thumbnail was
  *   uploaded using multipart/form-data under ᐸfile_attach_nameᐳ.
  * @param caption
  *   <em>Optional</em>. Caption of the audio to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   <em>Optional</em>. Mode for parsing entities in the audio caption. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param captionEntities
  *   <em>Optional</em>. List of special entities that appear in the caption, which can be specified instead of
  *   <em>parse_mode</em>
  * @param duration
  *   <em>Optional</em>. Duration of the audio in seconds
  * @param performer
  *   <em>Optional</em>. Performer of the audio
  * @param title
  *   <em>Optional</em>. Title of the audio
  */
final case class InputMediaAudio(
  media: IFile,
  thumbnail: Option[IFile] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  duration: Option[Int] = Option.empty,
  performer: Option[String] = Option.empty,
  title: Option[String] = Option.empty
) extends InputMedia
