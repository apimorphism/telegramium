package telegramium.bots.client

import telegramium.bots.InputRichMessage

/** @param chatId
  *   Unique identifier for the target private chat
  * @param draftId
  *   Unique identifier of the message draft; must be non-zero. Changes to drafts with the same identifier are animated.
  * @param richMessage
  *   The partial message to be streamed. Direct upload of new files isn't supported.
  * @param messageThreadId
  *   Unique identifier for the target message thread
  */
final case class SendRichMessageDraftReq(
  chatId: Long,
  draftId: Int,
  richMessage: InputRichMessage,
  messageThreadId: Option[Int] = Option.empty
)
