package telegramium.bots.client

import telegramium.bots.ChatId

final case class DeleteChatPhotoReq(
    /** Unique identifier for the target chat or username of the
      * target channel (in the format &#064;channelusername)*/
    chatId: ChatId)
