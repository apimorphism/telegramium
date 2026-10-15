package telegramium.bots

sealed trait InputProfilePhoto {}

/** An animated profile photo in the MPEG4 format.
  *
  * @param animation
  *   The animated profile photo. Profile photos can't be reused and can only be uploaded as a new file, so you can pass
  *   “attach://&lt;file_attach_name&gt;” if the photo was uploaded using multipart/form-data under
  *   &lt;file_attach_name&gt;.
  * @param mainFrameTimestamp
  *   <em>Optional</em>. Timestamp in seconds of the frame that will be used as the static profile photo. Defaults to
  *   0.0.
  */
final case class InputProfilePhotoAnimated(animation: IFile, mainFrameTimestamp: Option[Float] = Option.empty)
    extends InputProfilePhoto

/** A static profile photo in the .JPG format.
  *
  * @param photo
  *   The static profile photo. Profile photos can't be reused and can only be uploaded as a new file, so you can pass
  *   “attach://&lt;file_attach_name&gt;” if the photo was uploaded using multipart/form-data under
  *   &lt;file_attach_name&gt;.
  */
final case class InputProfilePhotoStatic(photo: IFile) extends InputProfilePhoto
