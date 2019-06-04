package telegramium.bots.client

import telegramium.bots.UserProfilePhotos

final case class GetUserProfilePhotosRes(ok: Boolean,
                                         description: Option[String] = Option.empty,
                                         result: Option[UserProfilePhotos] = Option.empty)
