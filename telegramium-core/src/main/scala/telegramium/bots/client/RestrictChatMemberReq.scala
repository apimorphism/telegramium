package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ChatPermissions

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup (in the format
  *   &#064;supergroupusername)
  * @param userId
  *   Unique identifier of the target user
  * @param permissions
  *   A JSON-serialized object for new user permissions
  * @param untilDate
  *   Date when restrictions will be lifted for the user, unix time. If user is restricted for more than 366 days or
  *   less than 30 seconds from the current time, they are considered to be restricted forever
  */
final case class RestrictChatMemberReq(
  chatId: ChatId,
  userId: Int,
  permissions: ChatPermissions,
  untilDate: Option[Int] = Option.empty
)
