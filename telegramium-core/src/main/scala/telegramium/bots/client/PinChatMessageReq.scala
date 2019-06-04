package telegramium.bots.client

import telegramium.bots.ChatId

final case class PinChatMessageReq(
                                   /** Unique identifier for the target chat or username of the
                                     * target channel (in the format @channelusername)*/
                                   chatId: ChatId,
                                   /** Identifier of a message to pin*/
                                   messageId: Int,
                                   /** Pass True, if it is not necessary to send a notification to
                                     * all chat members about the new pinned message. Notifications
                                     * are always disabled in channels.*/
                                   disableNotification: Option[Boolean] = Option.empty)
