package telegramium.bots.client

import telegramium.bots.InputRichMessage

/** @param chatId
  *   Unique identifier for the target private chat
  * @param draftId
  *   Unique identifier of the message draft; must be non-zero. Changes to drafts with the same identifier are animated.
  *   Otherwise, the draft is replaced without animation.
  * @param richMessage
  *   The partial message to be streamed. Direct upload of new files and explicit upload of files by a URL isn't
  *   supported.
  * @param messageThreadId
  *   Unique identifier for the target message thread
  * @param canStop
  *   Pass True to show the user a button to stop further drafts. The bot will receive an Update
  *   “stopped_message_generation” if the user presses the button.
  * @param keepOnStop
  *   Pass True to keep the draft in the chat when the button is pressed. The draft will still disappear after a short
  *   time or if the bot sends a message. To fully preserve the partial draft, the bot should send it as a new message.
  */
final case class SendRichMessageDraftReq(
  chatId: Long,
  draftId: Int,
  richMessage: InputRichMessage,
  messageThreadId: Option[Int] = Option.empty,
  canStop: Option[Boolean] = Option.empty,
  keepOnStop: Option[Boolean] = Option.empty
)
