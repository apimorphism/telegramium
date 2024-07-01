package telegramium.bots

sealed trait InputPaidMedia {}

/** The paid media to send is a photo.
  *
  * @param media
  *   File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL
  *   for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to upload a new one using
  *   multipart/form-data under <file_attach_name> name.
  */
final case class InputPaidMediaPhoto(media: String) extends InputPaidMedia

/** The paid media to send is a video.
  *
  * @param media
  *   File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL
  *   for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to upload a new one using
  *   multipart/form-data under <file_attach_name> name.
  * @param thumbnail
  *   Optional. Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported
  *   server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height
  *   should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused
  *   and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was
  *   uploaded using multipart/form-data under <file_attach_name>.
  * @param width
  *   Optional. Video width
  * @param height
  *   Optional. Video height
  * @param duration
  *   Optional. Video duration in seconds
  * @param supportsStreaming
  *   Optional. Pass True if the uploaded video is suitable for streaming
  */
final case class InputPaidMediaVideo(
  media: String,
  thumbnail: Option[IFile] = Option.empty,
  width: Option[Int] = Option.empty,
  height: Option[Int] = Option.empty,
  duration: Option[Int] = Option.empty,
  supportsStreaming: Option[Boolean] = Option.empty
) extends InputPaidMedia
