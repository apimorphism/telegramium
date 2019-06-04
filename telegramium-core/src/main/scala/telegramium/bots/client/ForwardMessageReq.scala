package telegramium.bots.client

import telegramium.bots.ChatId

final case class ForwardMessageReq(
                                   /** Unique identifier for the target chat or username of the
                                     * target channel (in the format @channelusername)*/
                                   chatId: ChatId,
                                   /** Unique identifier for the chat where the original message
                                     * was sent (or channel username in the format
                                     * @channelusername)*/
                                   fromChatId: ChatId,
                                   /** Sends the message silently. Users will receive a
                                     * notification with no sound.*/
                                   disableNotification: Option[Boolean] = Option.empty,
                                   /** Message identifier in the chat specified in from_chat_id*/
                                   messageId: Int)
