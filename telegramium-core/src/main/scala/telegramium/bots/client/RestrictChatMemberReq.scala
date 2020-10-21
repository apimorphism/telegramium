package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ChatPermissions

final case class RestrictChatMemberReq(
                                       /** Unique identifier for the target chat or username of the
                                         * target supergroup (in the format @supergroupusername)*/
                                       chatId: ChatId,
                                       /** Unique identifier of the target user*/
                                       userId: Int,
                                       /** A JSON-serialized object for new user permissions*/
                                       permissions: ChatPermissions,
                                       /** Date when restrictions will be lifted for the user, unix
                                         * time. If user is restricted for more than 366 days or less
                                         * than 30 seconds from the current time, they are considered
                                         * to be restricted forever*/
                                       untilDate: Option[Int] = Option.empty)
