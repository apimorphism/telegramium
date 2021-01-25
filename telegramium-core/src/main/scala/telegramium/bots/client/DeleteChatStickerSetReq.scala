package telegramium.bots.client

import telegramium.bots.ChatId

final case class DeleteChatStickerSetReq(
    /** Unique identifier for the target chat or username of the
      * target supergroup (in the format &#064;supergroupusername)*/
    chatId: ChatId)
