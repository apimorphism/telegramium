package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target group or username of the target supergroup or channel (in the format
  *   &#064;channelusername)
  * @param userId
  *   Unique identifier of the target user
  * @param untilDate
  *   Date when the user will be unbanned, unix time. If user is banned for more than 366 days or less than 30 seconds
  *   from the current time they are considered to be banned forever. Applied for supergroups and channels only.
  * @param revokeMessages
  *   Pass True to delete all messages from the chat for the user that is being removed. If False, the user will be able
  *   to see messages in the group that were sent before the user was removed. Always True for supergroups and channels.
  */
final case class BanChatMemberReq(
  chatId: ChatId,
  userId: Long,
  untilDate: Option[Int] = Option.empty,
  revokeMessages: Option[Boolean] = Option.empty
)
