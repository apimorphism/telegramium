package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target bot or supergroup in the format
  *   <code>&#064;username</code>. Channel chats and channel direct messages chats aren't supported.
  * @param action
  *   Type of action to broadcast. Choose one, depending on what the user is about to receive: <em>typing</em> for <a
  *   href="https://core.telegram.org/bots/api#sendmessage">text messages</a>, <em>upload_photo</em> for <a
  *   href="https://core.telegram.org/bots/api#sendphoto">photos</a>, <em>record_video</em> or <em>upload_video</em> for
  *   <a href="https://core.telegram.org/bots/api#sendvideo">videos</a>, <em>record_voice</em> or <em>upload_voice</em>
  *   for <a href="https://core.telegram.org/bots/api#sendvoice">voice notes</a>, <em>upload_document</em> for <a
  *   href="https://core.telegram.org/bots/api#senddocument">general files</a>, <em>choose_sticker</em> for <a
  *   href="https://core.telegram.org/bots/api#sendsticker">stickers</a>, <em>find_location</em> for <a
  *   href="https://core.telegram.org/bots/api#sendlocation">location data</a>, <em>record_video_note</em> or
  *   <em>upload_video_note</em> for <a href="https://core.telegram.org/bots/api#sendvideonote">video notes</a>.
  * @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which the action will be sent
  * @param messageThreadId
  *   Unique identifier for the target message thread or topic of a forum; for supergroups and private chats of bots
  *   with forum topic mode enabled only
  */
final case class SendChatActionReq(
  chatId: ChatId,
  action: String,
  businessConnectionId: Option[String] = Option.empty,
  messageThreadId: Option[Int] = Option.empty
)
