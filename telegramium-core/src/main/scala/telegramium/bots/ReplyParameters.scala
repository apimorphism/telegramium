package telegramium.bots

/** Describes reply parameters for the message that is being sent.
  *
  * @param messageId
  *   <em>Optional</em>. Identifier of the message that will be replied to in the current chat, or in the chat
  *   <em>chat_id</em> if it is specified. Required if <em>ephemeral_message_id</em> isn't specified.
  * @param chatId
  *   <em>Optional</em>. If the message to be replied to is from a different chat, unique identifier for the chat or
  *   username of the bot, supergroup or channel in the format <code>&#064;username</code>. Not supported for messages
  *   sent on behalf of a business account, messages from channel direct messages chats and ephemeral messages.
  * @param ephemeralMessageId
  *   <em>Optional</em>. Identifier of the incoming ephemeral message that will be replied to in the current chat. A
  *   reply to an ephemeral message must itself be an ephemeral message. An ephemeral message may only be replied to
  *   within 15 seconds of being sent. Required if <em>message_id</em> isn't specified.
  * @param allowSendingWithoutReply
  *   <em>Optional</em>. Pass <em>True</em> if the message should be sent even if the specified message to be replied to
  *   is not found. Always <em>False</em> for replies in another chat or forum topic, and sent ephemeral messages.
  *   Always <em>True</em> for messages sent on behalf of a business account.
  * @param quote
  *   <em>Optional</em>. Quoted part of the message to be replied to; 0-1024 characters after entities parsing. The
  *   quote must be an exact substring of the message to be replied to, including <em>bold</em>, <em>italic</em>,
  *   <em>underline</em>, <em>strikethrough</em>, <em>spoiler</em>, <em>custom_emoji</em>, and <em>date_time</em>
  *   entities. The message will fail to send if the quote isn't found in the original message. Ignored for ephemeral
  *   messages.
  * @param quoteParseMode
  *   <em>Optional</em>. Mode for parsing entities in the quote. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details.
  * @param quoteEntities
  *   <em>Optional</em>. A JSON-serialized list of special entities that appear in the quote. It can be specified
  *   instead of <em>quote_parse_mode</em>.
  * @param quotePosition
  *   <em>Optional</em>. Position of the quote in the original message in UTF-16 code units
  * @param checklistTaskId
  *   <em>Optional</em>. Identifier of the specific checklist task to be replied to
  * @param pollOptionId
  *   <em>Optional</em>. Persistent identifier of the specific poll option to be replied to
  */
final case class ReplyParameters(
  messageId: Option[Int] = Option.empty,
  chatId: Option[ChatId] = Option.empty,
  ephemeralMessageId: Option[Int] = Option.empty,
  allowSendingWithoutReply: Option[Boolean] = Option.empty,
  quote: Option[String] = Option.empty,
  quoteParseMode: Option[ParseMode] = Option.empty,
  quoteEntities: List[MessageEntity] = List.empty,
  quotePosition: Option[Int] = Option.empty,
  checklistTaskId: Option[Int] = Option.empty,
  pollOptionId: Option[String] = Option.empty
)
