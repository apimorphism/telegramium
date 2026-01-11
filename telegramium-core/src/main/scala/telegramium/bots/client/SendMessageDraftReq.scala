package telegramium.bots.client

import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity

/** @param chatId
  *   Unique identifier for the target private chat
  * @param draftId
  *   Unique identifier of the message draft; must be non-zero. Changes of drafts with the same identifier are animated
  * @param text
  *   Text of the message to be sent, 1-4096 characters after entities parsing
  * @param messageThreadId
  *   Unique identifier for the target message thread
  * @param parseMode
  *   Mode for parsing entities in the message text. See formatting options for more details.
  * @param entities
  *   A JSON-serialized list of special entities that appear in message text, which can be specified instead of
  *   parse_mode
  */
final case class SendMessageDraftReq(
  chatId: Long,
  draftId: Int,
  text: String,
  messageThreadId: Option[Int] = Option.empty,
  parseMode: Option[ParseMode] = Option.empty,
  entities: List[MessageEntity] = List.empty
)
