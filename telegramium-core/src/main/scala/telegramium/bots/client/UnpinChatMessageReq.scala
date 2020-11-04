package telegramium.bots.client

import telegramium.bots.ChatId

final case class UnpinChatMessageReq(
                                     /** Unique identifier for the target chat or username of the
                                       * target channel (in the format @channelusername)*/
                                     chatId: ChatId,
                                     /** Identifier of a message to unpin. If not specified, the
                                       * most recent pinned message (by sending date) will be
                                       * unpinned.*/
                                     messageId: Option[Int] = Option.empty)
