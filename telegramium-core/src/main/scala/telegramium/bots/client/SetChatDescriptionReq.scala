package telegramium.bots.client

import telegramium.bots.ChatId

/**
 * @param chatId Unique identifier for the target chat or username of the
 * target channel (in the format &#064;channelusername)
 * @param description New chat description, 0-255 characters
 */
final case class SetChatDescriptionReq(chatId: ChatId, description: Option[String] = Option.empty)
