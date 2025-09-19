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
  * @param useIndependentChatPermissions
  *   Pass True if chat permissions are set independently. Otherwise, the can_send_other_messages and
  *   can_add_web_page_previews permissions will imply the can_send_messages, can_send_audios, can_send_documents,
  *   can_send_photos, can_send_videos, can_send_video_notes, and can_send_voice_notes permissions; the can_send_polls
  *   permission will imply the can_send_messages permission.
  * @param untilDate
  *   Date when restrictions will be lifted for the user; Unix time. If user is restricted for more than 366 days or
  *   less than 30 seconds from the current time, they are considered to be restricted forever
  */
final case class RestrictChatMemberReq(
  chatId: ChatId,
  userId: Long,
  permissions: ChatPermissions,
  useIndependentChatPermissions: Option[Boolean] = Option.empty,
  untilDate: Option[Long] = Option.empty
)
