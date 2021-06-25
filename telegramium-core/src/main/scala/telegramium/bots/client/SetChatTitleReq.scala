package telegramium.bots.client

import telegramium.bots.ChatId

/**
 * @param chatId Unique identifier for the target chat or username of the
 * target channel (in the format &#064;channelusername)
 * @param title New chat title, 1-255 characters
 */
final case class SetChatTitleReq(chatId: ChatId, title: String)
