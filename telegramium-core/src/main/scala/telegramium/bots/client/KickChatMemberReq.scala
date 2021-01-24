package telegramium.bots.client

import telegramium.bots.ChatId

final case class KickChatMemberReq(
                                   /** Unique identifier for the target group or username of the
                                     * target supergroup or channel (in the format
                                     * @channelusername)*/
                                   chatId: ChatId,
                                   /** Unique identifier of the target user*/
                                   userId: Int,
                                   /** Date when the user will be unbanned, unix time. If user is
                                     * banned for more than 366 days or less than 30 seconds from
                                     * the current time they are considered to be banned forever.
                                     * Applied for supergroups and channels only.*/
                                   untilDate: Option[Int] = Option.empty)
