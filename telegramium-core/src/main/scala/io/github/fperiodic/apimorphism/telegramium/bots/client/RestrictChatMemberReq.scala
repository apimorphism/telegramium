package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.ChatId

final case class RestrictChatMemberReq(
                                       /** Unique identifier for the target chat or username of the
                                         * target supergroup (in the format @supergroupusername)*/
                                       chatId: ChatId,
                                       /** Unique identifier of the target user*/
                                       userId: Int,
                                       /** Date when restrictions will be lifted for the user, unix
                                         * time. If user is restricted for more than 366 days or less
                                         * than 30 seconds from the current time, they are considered
                                         * to be restricted forever*/
                                       untilDate: Option[Int] = Option.empty,
                                       /** Pass True, if the user can send text messages, contacts,
                                         * locations and venues*/
                                       canSendMessages: Option[Boolean] = Option.empty,
                                       /** Pass True, if the user can send audios, documents, photos,
                                         * videos, video notes and voice notes, implies
                                         * can_send_messages*/
                                       canSendMediaMessages: Option[Boolean] = Option.empty,
                                       /** Pass True, if the user can send animations, games, stickers
                                         * and use inline bots, implies can_send_media_messages*/
                                       canSendOtherMessages: Option[Boolean] = Option.empty,
                                       /** Pass True, if the user may add web page previews to their
                                         * messages, implies can_send_media_messages*/
                                       canAddWebPagePreviews: Option[Boolean] = Option.empty)
