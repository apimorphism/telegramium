package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ChatPermissions

final case class SetChatPermissionsReq(
                                       /** Unique identifier for the target chat or username of the
                                         * target supergroup (in the format &#064;supergroupusername)*/
                                       chatId: ChatId,
                                       /** New default chat permissions*/
                                       permissions: ChatPermissions)
