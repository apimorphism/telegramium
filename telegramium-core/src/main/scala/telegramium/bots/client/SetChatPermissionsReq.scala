package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ChatPermissions

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup (in the format
  *   &#064;supergroupusername)
  * @param permissions
  *   A JSON-serialized object for new default chat permissions
  * @param useIndependentChatPermissions
  *   Pass True if chat permissions are set independently. Otherwise, the can_send_other_messages and
  *   can_add_web_page_previews permissions will imply the can_send_messages, can_send_audios, can_send_documents,
  *   can_send_photos, can_send_videos, can_send_video_notes, and can_send_voice_notes permissions; the can_send_polls
  *   permission will imply the can_send_messages permission.
  */
final case class SetChatPermissionsReq(
  chatId: ChatId,
  permissions: ChatPermissions,
  useIndependentChatPermissions: Option[Boolean] = Option.empty
)
