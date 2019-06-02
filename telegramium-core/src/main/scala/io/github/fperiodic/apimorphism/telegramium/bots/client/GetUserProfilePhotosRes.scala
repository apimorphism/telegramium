package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.UserProfilePhotos

final case class GetUserProfilePhotosRes(ok: Boolean,
                                         description: Option[String] = Option.empty,
                                         result: Option[UserProfilePhotos] = Option.empty)
