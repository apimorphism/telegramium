package io.github.fperiodic.apimorphism.telegramium.bots.client

final case class GetUserProfilePhotosReq(
                                         /** Unique identifier of the target user*/
                                         userId: Int,
                                         /** Sequential number of the first photo to be returned. By
                                           * default, all photos are returned.*/
                                         offset: Option[Int] = Option.empty,
                                         /** Limits the number of photos to be retrieved. Values between
                                           * 1—100 are accepted. Defaults to 100.*/
                                         limit: Option[Int] = Option.empty)
