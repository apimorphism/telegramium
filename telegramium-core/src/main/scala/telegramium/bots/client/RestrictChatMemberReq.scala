package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ChatPermissions

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup in the format
  *   <code>&#064;username</code>
  * @param userId
  *   Unique identifier of the target user
  * @param permissions
  *   A JSON-serialized object for new user permissions
  * @param useIndependentChatPermissions
  *   Pass <em>True</em> if chat permissions are set independently. Otherwise, the <em>can_send_other_messages</em> and
  *   <em>can_add_web_page_previews</em> permissions will imply the <em>can_send_messages</em>,
  *   <em>can_send_audios</em>, <em>can_send_documents</em>, <em>can_send_photos</em>, <em>can_send_videos</em>,
  *   <em>can_send_video_notes</em>, and <em>can_send_voice_notes</em> permissions; the <em>can_send_polls</em>
  *   permission will imply the <em>can_send_messages</em> permission.
  * @param untilDate
  *   Date when restrictions will be lifted for the user; Unix time. If user is restricted for more than 366 days or
  *   less than 30 seconds from the current time, they are considered to be restricted forever.
  */
final case class RestrictChatMemberReq(
  chatId: ChatId,
  userId: Long,
  permissions: ChatPermissions,
  useIndependentChatPermissions: Option[Boolean] = Option.empty,
  untilDate: Option[Long] = Option.empty
)
