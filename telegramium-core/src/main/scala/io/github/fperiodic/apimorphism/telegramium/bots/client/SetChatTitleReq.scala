package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.ChatId

final case class SetChatTitleReq(
                                 /** Unique identifier for the target chat or username of the
                                   * target channel (in the format @channelusername)*/
                                 chatId: ChatId,
                                 /** New chat title, 1-255 characters*/
                                 title: String)
