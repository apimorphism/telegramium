package telegramium.bots

/** At most one of the optional fields can be present in any given object.
  *
  * @param animation
  *   Optional. Media is an animation, information about the animation
  * @param audio
  *   Optional. Media is an audio file, information about the file; currently, can't be received in a poll option
  * @param document
  *   Optional. Media is a general file, information about the file; currently, can't be received in a poll option
  * @param livePhoto
  *   Optional. Media is a live photo, information about the live photo
  * @param location
  *   Optional. Media is a shared location, information about the location
  * @param photo
  *   Optional. Media is a photo, available sizes of the photo
  * @param sticker
  *   Optional. Media is a sticker, information about the sticker; currently, for poll options only
  * @param venue
  *   Optional. Media is a venue, information about the venue
  * @param video
  *   Optional. Media is a video, information about the video
  */
final case class PollMedia(
  animation: Option[Animation] = Option.empty,
  audio: Option[Audio] = Option.empty,
  document: Option[Document] = Option.empty,
  livePhoto: Option[LivePhoto] = Option.empty,
  location: Option[Location] = Option.empty,
  photo: List[PhotoSize] = List.empty,
  sticker: Option[Sticker] = Option.empty,
  venue: Option[Venue] = Option.empty,
  video: Option[Video] = Option.empty
)
