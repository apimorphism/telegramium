package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel (in the format &#064;channelusername)
  * @param action
  *   Type of action to broadcast. Choose one, depending on what the user is about to receive: typing for text messages,
  *   upload_photo for photos, record_video or upload_video for videos, record_voice or upload_voice for voice notes,
  *   upload_document for general files, find_location for location data, record_video_note or upload_video_note for
  *   video notes.
  */
final case class SendChatActionReq(chatId: ChatId, action: String)
