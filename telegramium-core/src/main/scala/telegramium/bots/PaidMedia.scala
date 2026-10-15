package telegramium.bots

sealed trait PaidMedia {}

/** The paid media is a <a href="https://core.telegram.org/bots/api#livephoto">live photo</a>.
  *
  * @param livePhoto
  *   The photo
  */
final case class PaidMediaLivePhoto(livePhoto: LivePhoto) extends PaidMedia

/** The paid media is a video.
  *
  * @param video
  *   The video
  */
final case class PaidMediaVideo(video: Video) extends PaidMedia

/** The paid media is a photo.
  *
  * @param photo
  *   The photo
  */
final case class PaidMediaPhoto(photo: List[PhotoSize] = List.empty) extends PaidMedia

/** The paid media isn't available before the payment.
  *
  * @param width
  *   <em>Optional</em>. Media width as defined by the sender
  * @param height
  *   <em>Optional</em>. Media height as defined by the sender
  * @param duration
  *   <em>Optional</em>. Duration of the media in seconds as defined by the sender
  */
final case class PaidMediaPreview(
  width: Option[Int] = Option.empty,
  height: Option[Int] = Option.empty,
  duration: Option[Int] = Option.empty
) extends PaidMedia
