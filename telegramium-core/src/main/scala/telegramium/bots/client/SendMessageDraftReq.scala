package telegramium.bots.client

import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity

/** @param chatId
  *   Unique identifier for the target private chat
  * @param draftId
  *   Unique identifier of the message draft; must be non-zero. Changes of drafts with the same identifier are animated.
  * @param messageThreadId
  *   Unique identifier for the target message thread
  * @param text
  *   Text of the message to be sent, 0-4096 characters after entities parsing. Pass an empty text to show a “Thinking…”
  *   placeholder.
  * @param parseMode
  *   Mode for parsing entities in the message text. See formatting options for more details.
  * @param entities
  *   A JSON-serialized list of special entities that appear in message text, which can be specified instead of
  *   parse_mode
  */
final case class SendMessageDraftReq(
  chatId: Long,
  draftId: Int,
  messageThreadId: Option[Int] = Option.empty,
  text: Option[String] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  entities: List[MessageEntity] = List.empty
)
