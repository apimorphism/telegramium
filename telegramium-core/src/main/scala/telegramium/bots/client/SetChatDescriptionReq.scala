package telegramium.bots.client

import telegramium.bots.ChatId

final case class SetChatDescriptionReq(
                                       /** Unique identifier for the target chat or username of the
                                         * target channel (in the format &#064;channelusername)*/
                                       chatId: ChatId,
                                       /** New chat description, 0-255 characters*/
                                       description: Option[String] = Option.empty)
