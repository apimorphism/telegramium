package io.github.fperiodic.apimorphism.telegramium.bots

/** This object represent a user's profile pictures.*/
final case class UserProfilePhotos(
                                   /** Total number of profile pictures the target user has*/
                                   totalCount: Int,
                                   /** Requested profile pictures (in up to 4 sizes each)*/
                                   photos: List[List[PhotoSize]] = List.empty)
