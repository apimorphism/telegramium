package telegramium.bots

/** This object represent a user's profile pictures.
  *
  * @param totalCount
  *   Total number of profile pictures the target user has
  * @param photos
  *   Requested profile pictures (in up to 4 sizes each)
  */
final case class UserProfilePhotos(totalCount: Int, photos: List[List[PhotoSize]] = List.empty)
