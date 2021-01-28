package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId Unique identifier for the target chat or username of the
  * target supergroup (in the format &#064;supergroupusername)
  * @param stickerSetName Name of the sticker set to be set as the group sticker set */
final case class SetChatStickerSetReq(chatId: ChatId, stickerSetName: String)
