package telegramium.bots.client

import telegramium.bots.ChatId

final case class UnpinAllChatMessagesReq(
    /** Unique identifier for the target chat or username of the
      * target channel (in the format @channelusername)*/
    chatId: ChatId)
