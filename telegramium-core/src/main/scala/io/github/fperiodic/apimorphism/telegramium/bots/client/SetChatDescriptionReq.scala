package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.ChatId

final case class SetChatDescriptionReq(
                                       /** Unique identifier for the target chat or username of the
                                         * target channel (in the format @channelusername)*/
                                       chatId: ChatId,
                                       /** New chat description, 0-255 characters*/
                                       description: Option[String] = Option.empty)
