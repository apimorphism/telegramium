package telegramium.bots

sealed trait InputPaidMedia {}

/** The paid media to send is a photo.
  *
  * @param media
  *   File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL
  *   for Telegram to get a file from the Internet, or pass “attach://&lt;file_attach_name&gt;” to upload a new one
  *   using multipart/form-data under &lt;file_attach_name&gt; name.
  */
final case class InputPaidMediaPhoto(media: IFile) extends InputPaidMedia

/** The paid media to send is a live photo.
  *
  * @param media
  *   Video of the live photo to send. Pass a file_id to send a file that exists on the Telegram servers (recommended)
  *   or pass “attach://&lt;file_attach_name&gt;” to upload a new one using multipart/form-data under
  *   &lt;file_attach_name&gt; name. Sending live photos by a URL is currently unsupported.
  * @param photo
  *   The static photo to send. Pass a file_id to send a file that exists on the Telegram servers (recommended) or pass
  *   “attach://&lt;file_attach_name&gt;” to upload a new one using multipart/form-data under &lt;file_attach_name&gt;
  *   name. Sending live photos by a URL is currently unsupported.
  */
final case class InputPaidMediaLivePhoto(media: IFile, photo: IFile) extends InputPaidMedia

/** The paid media to send is a video.
  *
  * @param media
  *   File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL
  *   for Telegram to get a file from the Internet, or pass “attach://&lt;file_attach_name&gt;” to upload a new one
  *   using multipart/form-data under &lt;file_attach_name&gt; name.
  * @param thumbnail
  *   <em>Optional</em>. Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported
  *   server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height
  *   should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused
  *   and can be only uploaded as a new file, so you can pass “attach://&lt;file_attach_name&gt;” if the thumbnail was
  *   uploaded using multipart/form-data under &lt;file_attach_name&gt;.
  * @param cover
  *   <em>Optional</em>. Cover for the video in the message. Pass a file_id to send a file that exists on the Telegram
  *   servers (recommended), pass an HTTP URL for Telegram to get a file from the Internet, or pass
  *   “attach://&lt;file_attach_name&gt;” to upload a new one using multipart/form-data under &lt;file_attach_name&gt;
  *   name.
  * @param startTimestamp
  *   <em>Optional</em>. Start timestamp for the video in the message
  * @param width
  *   <em>Optional</em>. Video width
  * @param height
  *   <em>Optional</em>. Video height
  * @param duration
  *   <em>Optional</em>. Video duration in seconds
  * @param supportsStreaming
  *   <em>Optional</em>. Pass <em>True</em> if the uploaded video is suitable for streaming
  */
final case class InputPaidMediaVideo(
  media: IFile,
  thumbnail: Option[IFile] = Option.empty,
  cover: Option[IFile] = Option.empty,
  startTimestamp: Option[Int] = Option.empty,
  width: Option[Int] = Option.empty,
  height: Option[Int] = Option.empty,
  duration: Option[Int] = Option.empty,
  supportsStreaming: Option[Boolean] = Option.empty
) extends InputPaidMedia
