package telegramium.bots.client

/** @param userId
  *   Unique identifier of the target user
  * @param offset
  *   Sequential number of the first photo to be returned. By default, all photos are returned.
  * @param limit
  *   Limits the number of photos to be retrieved. Values between 1-100 are accepted. Defaults to 100.
  */
final case class GetUserProfilePhotosReq(
  userId: Long,
  offset: Option[Int] = Option.empty,
  limit: Option[Int] = Option.empty
)
