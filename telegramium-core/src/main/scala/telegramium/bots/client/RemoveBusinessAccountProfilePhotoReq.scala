package telegramium.bots.client

/** @param businessConnectionId
  *   Unique identifier of the business connection
  * @param isPublic
  *   Pass True to remove the public photo, which is visible even if the main photo is hidden by the business account's
  *   privacy settings. After the main photo is removed, the previous profile photo (if present) becomes the main photo.
  */
final case class RemoveBusinessAccountProfilePhotoReq(
  businessConnectionId: String,
  isPublic: Option[Boolean] = Option.empty
)
