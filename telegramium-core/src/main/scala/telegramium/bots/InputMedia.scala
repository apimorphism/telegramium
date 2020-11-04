package telegramium.bots

sealed trait InputMedia {}

/** Represents an animation file (GIF or H.264/MPEG-4 AVC video without sound) to
  * be sent.*/
final case class InputMediaAnimation(
                                     /** File to send. Pass a file_id to send a file that exists on
                                       * the Telegram servers (recommended), pass an HTTP URL for
                                       * Telegram to get a file from the Internet, or pass
                                       * “attach://<file_attach_name>” to upload a new one using
                                       * multipart/form-data under <file_attach_name> name. More info
                                       * on Sending Files »*/
                                     media: String,
                                     /** Optional. Thumbnail of the file sent; can be ignored if
                                       * thumbnail generation for the file is supported server-side.
                                       * The thumbnail should be in JPEG format and less than 200 kB
                                       * in size. A thumbnail's width and height should not exceed
                                       * 320. Ignored if the file is not uploaded using
                                       * multipart/form-data. Thumbnails can't be reused and can be
                                       * only uploaded as a new file, so you can pass
                                       * “attach://<file_attach_name>” if the thumbnail was uploaded
                                       * using multipart/form-data under <file_attach_name>. More
                                       * info on Sending Files »*/
                                     thumb: Option[IFile] = Option.empty,
                                     /** Optional. Caption of the animation to be sent, 0-1024
                                       * characters after entities parsing*/
                                     caption: Option[String] = Option.empty,
                                     /** Optional. Mode for parsing entities in the animation
                                       * caption. See formatting options for more details.*/
                                     parseMode: Option[ParseMode] = Option.empty,
                                     /** Optional. List of special entities that appear in the
                                       * caption, which can be specified instead of parse_mode*/
                                     captionEntities: List[MessageEntity] = List.empty,
                                     /** Optional. Animation width*/
                                     width: Option[Int] = Option.empty,
                                     /** Optional. Animation height*/
                                     height: Option[Int] = Option.empty,
                                     /** Optional. Animation duration*/
                                     duration: Option[Int] = Option.empty)
    extends InputMedia

/** Represents a photo to be sent.*/
final case class InputMediaPhoto(
                                 /** File to send. Pass a file_id to send a file that exists on
                                   * the Telegram servers (recommended), pass an HTTP URL for
                                   * Telegram to get a file from the Internet, or pass
                                   * “attach://<file_attach_name>” to upload a new one using
                                   * multipart/form-data under <file_attach_name> name. More info
                                   * on Sending Files »*/
                                 media: String,
                                 /** Optional. Caption of the photo to be sent, 0-1024
                                   * characters after entities parsing*/
                                 caption: Option[String] = Option.empty,
                                 /** Optional. Mode for parsing entities in the photo caption.
                                   * See formatting options for more details.*/
                                 parseMode: Option[ParseMode] = Option.empty,
                                 /** Optional. List of special entities that appear in the
                                   * caption, which can be specified instead of parse_mode*/
                                 captionEntities: List[MessageEntity] = List.empty)
    extends InputMedia

/** Represents a video to be sent.*/
final case class InputMediaVideo(
                                 /** File to send. Pass a file_id to send a file that exists on
                                   * the Telegram servers (recommended), pass an HTTP URL for
                                   * Telegram to get a file from the Internet, or pass
                                   * “attach://<file_attach_name>” to upload a new one using
                                   * multipart/form-data under <file_attach_name> name. More info
                                   * on Sending Files »*/
                                 media: String,
                                 /** Optional. Thumbnail of the file sent; can be ignored if
                                   * thumbnail generation for the file is supported server-side.
                                   * The thumbnail should be in JPEG format and less than 200 kB
                                   * in size. A thumbnail's width and height should not exceed
                                   * 320. Ignored if the file is not uploaded using
                                   * multipart/form-data. Thumbnails can't be reused and can be
                                   * only uploaded as a new file, so you can pass
                                   * “attach://<file_attach_name>” if the thumbnail was uploaded
                                   * using multipart/form-data under <file_attach_name>. More
                                   * info on Sending Files »*/
                                 thumb: Option[IFile] = Option.empty,
                                 /** Optional. Caption of the video to be sent, 0-1024
                                   * characters after entities parsing*/
                                 caption: Option[String] = Option.empty,
                                 /** Optional. Mode for parsing entities in the video caption.
                                   * See formatting options for more details.*/
                                 parseMode: Option[ParseMode] = Option.empty,
                                 /** Optional. List of special entities that appear in the
                                   * caption, which can be specified instead of parse_mode*/
                                 captionEntities: List[MessageEntity] = List.empty,
                                 /** Optional. Video width*/
                                 width: Option[Int] = Option.empty,
                                 /** Optional. Video height*/
                                 height: Option[Int] = Option.empty,
                                 /** Optional. Video duration*/
                                 duration: Option[Int] = Option.empty,
                                 /** Optional. Pass True, if the uploaded video is suitable for
                                   * streaming*/
                                 supportsStreaming: Option[Boolean] = Option.empty)
    extends InputMedia

/** Represents a general file to be sent.*/
final case class InputMediaDocument(
                                    /** File to send. Pass a file_id to send a file that exists on
                                      * the Telegram servers (recommended), pass an HTTP URL for
                                      * Telegram to get a file from the Internet, or pass
                                      * “attach://<file_attach_name>” to upload a new one using
                                      * multipart/form-data under <file_attach_name> name. More info
                                      * on Sending Files »*/
                                    media: String,
                                    /** Optional. Thumbnail of the file sent; can be ignored if
                                      * thumbnail generation for the file is supported server-side.
                                      * The thumbnail should be in JPEG format and less than 200 kB
                                      * in size. A thumbnail's width and height should not exceed
                                      * 320. Ignored if the file is not uploaded using
                                      * multipart/form-data. Thumbnails can't be reused and can be
                                      * only uploaded as a new file, so you can pass
                                      * “attach://<file_attach_name>” if the thumbnail was uploaded
                                      * using multipart/form-data under <file_attach_name>. More
                                      * info on Sending Files »*/
                                    thumb: Option[IFile] = Option.empty,
                                    /** Optional. Caption of the document to be sent, 0-1024
                                      * characters after entities parsing*/
                                    caption: Option[String] = Option.empty,
                                    /** Optional. Mode for parsing entities in the document
                                      * caption. See formatting options for more details.*/
                                    parseMode: Option[ParseMode] = Option.empty,
                                    /** Optional. List of special entities that appear in the
                                      * caption, which can be specified instead of parse_mode*/
                                    captionEntities: List[MessageEntity] = List.empty,
                                    /** Optional. Disables automatic server-side content type
                                      * detection for files uploaded using multipart/form-data.
                                      * Always true, if the document is sent as part of an album.*/
                                    disableContentTypeDetection: Option[Boolean] = Option.empty)
    extends InputMedia

/** Represents an audio file to be treated as music to be sent.*/
final case class InputMediaAudio(
                                 /** File to send. Pass a file_id to send a file that exists on
                                   * the Telegram servers (recommended), pass an HTTP URL for
                                   * Telegram to get a file from the Internet, or pass
                                   * “attach://<file_attach_name>” to upload a new one using
                                   * multipart/form-data under <file_attach_name> name. More info
                                   * on Sending Files »*/
                                 media: String,
                                 /** Optional. Thumbnail of the file sent; can be ignored if
                                   * thumbnail generation for the file is supported server-side.
                                   * The thumbnail should be in JPEG format and less than 200 kB
                                   * in size. A thumbnail's width and height should not exceed
                                   * 320. Ignored if the file is not uploaded using
                                   * multipart/form-data. Thumbnails can't be reused and can be
                                   * only uploaded as a new file, so you can pass
                                   * “attach://<file_attach_name>” if the thumbnail was uploaded
                                   * using multipart/form-data under <file_attach_name>. More
                                   * info on Sending Files »*/
                                 thumb: Option[IFile] = Option.empty,
                                 /** Optional. Caption of the audio to be sent, 0-1024
                                   * characters after entities parsing*/
                                 caption: Option[String] = Option.empty,
                                 /** Optional. Mode for parsing entities in the audio caption.
                                   * See formatting options for more details.*/
                                 parseMode: Option[ParseMode] = Option.empty,
                                 /** Optional. List of special entities that appear in the
                                   * caption, which can be specified instead of parse_mode*/
                                 captionEntities: List[MessageEntity] = List.empty,
                                 /** Optional. Duration of the audio in seconds*/
                                 duration: Option[Int] = Option.empty,
                                 /** Optional. Performer of the audio*/
                                 performer: Option[String] = Option.empty,
                                 /** Optional. Title of the audio*/
                                 title: Option[String] = Option.empty)
    extends InputMedia
