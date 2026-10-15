package telegramium.bots

/** At most <strong>one</strong> of the optional fields can be present in any given object.
  *
  * @param animation
  *   <em>Optional</em>. Media is an animation, information about the animation
  * @param audio
  *   <em>Optional</em>. Media is an audio file, information about the file; currently, can't be received in a poll
  *   option
  * @param document
  *   <em>Optional</em>. Media is a general file, information about the file; currently, can't be received in a poll
  *   option
  * @param link
  *   <em>Optional</em>. The HTTP link attached to the poll option
  * @param livePhoto
  *   <em>Optional</em>. Media is a live photo, information about the live photo
  * @param location
  *   <em>Optional</em>. Media is a shared location, information about the location
  * @param photo
  *   <em>Optional</em>. Media is a photo, available sizes of the photo
  * @param sticker
  *   <em>Optional</em>. Media is a sticker, information about the sticker; currently, for poll options only
  * @param venue
  *   <em>Optional</em>. Media is a venue, information about the venue
  * @param video
  *   <em>Optional</em>. Media is a video, information about the video
  */
final case class PollMedia(
  animation: Option[Animation] = Option.empty,
  audio: Option[Audio] = Option.empty,
  document: Option[Document] = Option.empty,
  link: Option[Link] = Option.empty,
  livePhoto: Option[LivePhoto] = Option.empty,
  location: Option[Location] = Option.empty,
  photo: List[PhotoSize] = List.empty,
  sticker: Option[Sticker] = Option.empty,
  venue: Option[Venue] = Option.empty,
  video: Option[Video] = Option.empty
)
