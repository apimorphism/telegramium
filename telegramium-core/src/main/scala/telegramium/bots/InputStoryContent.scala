package telegramium.bots

sealed trait InputStoryContent {}

/** Describes a photo to post as a story.
  *
  * @param photo
  *   The photo to post as a story. The photo must be of the size 1080x1920 and must not exceed 10 MB. The photo can't
  *   be reused and can only be uploaded as a new file, so you can pass “attach://&lt;file_attach_name&gt;” if the photo
  *   was uploaded using multipart/form-data under &lt;file_attach_name&gt;.
  */
final case class InputStoryContentPhoto(photo: IFile) extends InputStoryContent

/** Describes a video to post as a story.
  *
  * @param video
  *   The video to post as a story. The video must be of the size 720x1280, streamable, encoded with H.265 codec, with
  *   key frames added each second in the MPEG4 format, and must not exceed 30 MB. The video can't be reused and can
  *   only be uploaded as a new file, so you can pass “attach://&lt;file_attach_name&gt;” if the video was uploaded
  *   using multipart/form-data under &lt;file_attach_name&gt;.
  * @param duration
  *   <em>Optional</em>. Precise duration of the video in seconds; 0-60
  * @param coverFrameTimestamp
  *   <em>Optional</em>. Timestamp in seconds of the frame that will be used as the static cover for the story. Defaults
  *   to 0.0.
  * @param isAnimation
  *   <em>Optional</em>. Pass <em>True</em> if the video has no sound
  */
final case class InputStoryContentVideo(
  video: IFile,
  duration: Option[Float] = Option.empty,
  coverFrameTimestamp: Option[Float] = Option.empty,
  isAnimation: Option[Boolean] = Option.empty
) extends InputStoryContent
