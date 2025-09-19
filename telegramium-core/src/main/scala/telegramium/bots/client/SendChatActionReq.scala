package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target supergroup (in the format
  *   &#064;supergroupusername). Channel chats and channel direct messages chats aren't supported.
  * @param action
  *   Type of action to broadcast. Choose one, depending on what the user is about to receive: typing for text messages,
  *   upload_photo for photos, record_video or upload_video for videos, record_voice or upload_voice for voice notes,
  *   upload_document for general files, choose_sticker for stickers, find_location for location data, record_video_note
  *   or upload_video_note for video notes.
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the action will be sent
  * @param messageThreadId
  *   Unique identifier for the target message thread; for supergroups only
  */
final case class SendChatActionReq(
  chatId: ChatId,
  action: String,
  businessConnectionId: Option[String] = Option.empty,
  messageThreadId: Option[Int] = Option.empty
)
