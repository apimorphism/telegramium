package telegramium.bots.client

import telegramium.bots.ChatId
import telegramium.bots.ParseMode
import telegramium.bots.MessageEntity

/** @param chatId
  *   Unique identifier for the target chat or username of the target bot, supergroup, or channel in the format
  *   <code>&#064;username</code>. Channel direct messages chats can't be verified.
  * @param customDescription
  *   Custom description for the verification; 0-70 characters. Must be empty if the organization isn't allowed to
  *   provide a custom verification description.
  * @param customDescriptionParseMode
  *   Mode for parsing entities in the custom description. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details. Only
  *   “text_link” entities are supported.
  * @param customDescriptionEntities
  *   A JSON-serialized list of special entities that appear in the custom description, which can be specified instead
  *   of <em>custom_description_parse_mode</em>. Only “text_link” entities are supported.
  */
final case class VerifyChatReq(
  chatId: ChatId,
  customDescription: Option[String] = Option.empty,
  customDescriptionParseMode: Option[ParseMode] = Option.empty,
  customDescriptionEntities: List[MessageEntity] = List.empty
)
