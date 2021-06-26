package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ChatPermissions

/** @param chatId Unique identifier for the target chat or username of the
  * target supergroup (in the format &#064;supergroupusername)
  * @param permissions New default chat permissions */
final case class SetChatPermissionsReq(chatId: ChatId, permissions: ChatPermissions)
