package telegramium.bots.client

import telegramium.bots.ChatId

final case class UnpinAllChatMessagesReq(
    /** Unique identifier for the target chat or username of the
      * target channel (in the format &#064;channelusername)*/
    chatId: ChatId)
