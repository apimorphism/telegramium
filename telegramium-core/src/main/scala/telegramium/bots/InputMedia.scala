package telegramium.bots

sealed trait InputMedia {}

/** Represents an animation file (GIF or H.264/MPEG-4 AVC video without sound) to be sent.
  *
  * @param media
  *   File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL
  *   for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to upload a new one using
  *   multipart/form-data under <file_attach_name> name.
  * @param thumb
  *   Optional. Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported
  *   server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height
  *   should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused
  *   and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was
  *   uploaded using multipart/form-data under <file_attach_name>.
  * @param caption
  *   Optional. Caption of the animation to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   Optional. Mode for parsing entities in the animation caption. See formatting options for more details.
  * @param captionEntities
  *   Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
  * @param width
  *   Optional. Animation width
  * @param height
  *   Optional. Animation height
  * @param duration
  *   Optional. Animation duration in seconds
  * @param hasSpoiler
  *   Optional. Pass True if the animation needs to be covered with a spoiler animation
  */
final case class InputMediaAnimation(
  media: String,
  thumb: Option[IFile] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  width: Option[Int] = Option.empty,
  height: Option[Int] = Option.empty,
  duration: Option[Int] = Option.empty,
  hasSpoiler: Option[Boolean] = Option.empty
) extends InputMedia

/** Represents a photo to be sent.
  *
  * @param media
  *   File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL
  *   for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to upload a new one using
  *   multipart/form-data under <file_attach_name> name.
  * @param caption
  *   Optional. Caption of the photo to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   Optional. Mode for parsing entities in the photo caption. See formatting options for more details.
  * @param captionEntities
  *   Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
  * @param hasSpoiler
  *   Optional. Pass True if the photo needs to be covered with a spoiler animation
  */
final case class InputMediaPhoto(
  media: String,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  hasSpoiler: Option[Boolean] = Option.empty
) extends InputMedia

/** Represents a video to be sent.
  *
  * @param media
  *   File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL
  *   for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to upload a new one using
  *   multipart/form-data under <file_attach_name> name.
  * @param thumb
  *   Optional. Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported
  *   server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height
  *   should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused
  *   and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was
  *   uploaded using multipart/form-data under <file_attach_name>.
  * @param caption
  *   Optional. Caption of the video to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   Optional. Mode for parsing entities in the video caption. See formatting options for more details.
  * @param captionEntities
  *   Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
  * @param width
  *   Optional. Video width
  * @param height
  *   Optional. Video height
  * @param duration
  *   Optional. Video duration in seconds
  * @param supportsStreaming
  *   Optional. Pass True if the uploaded video is suitable for streaming
  * @param hasSpoiler
  *   Optional. Pass True if the video needs to be covered with a spoiler animation
  */
final case class InputMediaVideo(
  media: String,
  thumb: Option[IFile] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  width: Option[Int] = Option.empty,
  height: Option[Int] = Option.empty,
  duration: Option[Int] = Option.empty,
  supportsStreaming: Option[Boolean] = Option.empty,
  hasSpoiler: Option[Boolean] = Option.empty
) extends InputMedia

/** Represents a general file to be sent.
  *
  * @param media
  *   File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL
  *   for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to upload a new one using
  *   multipart/form-data under <file_attach_name> name.
  * @param thumb
  *   Optional. Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported
  *   server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height
  *   should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused
  *   and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was
  *   uploaded using multipart/form-data under <file_attach_name>.
  * @param caption
  *   Optional. Caption of the document to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   Optional. Mode for parsing entities in the document caption. See formatting options for more details.
  * @param captionEntities
  *   Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
  * @param disableContentTypeDetection
  *   Optional. Disables automatic server-side content type detection for files uploaded using multipart/form-data.
  *   Always True, if the document is sent as part of an album.
  */
final case class InputMediaDocument(
  media: String,
  thumb: Option[IFile] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  disableContentTypeDetection: Option[Boolean] = Option.empty
) extends InputMedia

/** Represents an audio file to be treated as music to be sent.
  *
  * @param media
  *   File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL
  *   for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to upload a new one using
  *   multipart/form-data under <file_attach_name> name.
  * @param thumb
  *   Optional. Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported
  *   server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height
  *   should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused
  *   and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was
  *   uploaded using multipart/form-data under <file_attach_name>.
  * @param caption
  *   Optional. Caption of the audio to be sent, 0-1024 characters after entities parsing
  * @param parseMode
  *   Optional. Mode for parsing entities in the audio caption. See formatting options for more details.
  * @param captionEntities
  *   Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode
  * @param duration
  *   Optional. Duration of the audio in seconds
  * @param performer
  *   Optional. Performer of the audio
  * @param title
  *   Optional. Title of the audio
  */
final case class InputMediaAudio(
  media: String,
  thumb: Option[IFile] = Option.empty,
  caption: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  captionEntities: List[MessageEntity] = List.empty,
  duration: Option[Int] = Option.empty,
  performer: Option[String] = Option.empty,
  title: Option[String] = Option.empty
) extends InputMedia
