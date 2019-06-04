package telegramium.bots.client

import telegramium.bots.ChatId

final case class LeaveChatReq(
    /** Unique identifier for the target chat or username of the
      * target supergroup or channel (in the format
      * @channelusername)*/
    chatId: ChatId)
