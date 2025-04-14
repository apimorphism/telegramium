package telegramium.bots.client

import telegramium.bots.InputProfilePhoto

/** @param businessConnectionId
  *   Unique identifier of the business connection
  * @param photo
  *   The new profile photo to set
  * @param isPublic
  *   Pass True to set the public photo, which will be visible even if the main photo is hidden by the business
  *   account's privacy settings. An account can have only one public photo.
  */
final case class SetBusinessAccountProfilePhotoReq(
  businessConnectionId: String,
  photo: InputProfilePhoto,
  isPublic: Option[Boolean] = Option.empty
)
