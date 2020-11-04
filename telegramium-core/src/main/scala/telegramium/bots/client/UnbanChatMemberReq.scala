package telegramium.bots.client

import telegramium.bots.ChatId

final case class UnbanChatMemberReq(
                                    /** Unique identifier for the target group or username of the
                                      * target supergroup or channel (in the format @username)*/
                                    chatId: ChatId,
                                    /** Unique identifier of the target user*/
                                    userId: Int,
                                    /** Do nothing if the user is not banned*/
                                    onlyIfBanned: Option[Boolean] = Option.empty)
