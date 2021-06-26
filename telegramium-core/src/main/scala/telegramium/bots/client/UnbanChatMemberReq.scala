package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId Unique identifier for the target group or username of the
  * target supergroup or channel (in the format &#064;username)
  * @param userId Unique identifier of the target user
  * @param onlyIfBanned Do nothing if the user is not banned */
final case class UnbanChatMemberReq(chatId: ChatId,
                                    userId: Int,
                                    onlyIfBanned: Option[Boolean] = Option.empty)
