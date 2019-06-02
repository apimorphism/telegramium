package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.ChatId

final case class UnbanChatMemberReq(
                                    /** Unique identifier for the target group or username of the
                                      * target supergroup or channel (in the format @username)*/
                                    chatId: ChatId,
                                    /** Unique identifier of the target user*/
                                    userId: Int)
