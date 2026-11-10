package telegramium.bots

/** Describes reply parameters for the message that is being sent.
  *
  * @param messageId
  *   <em>Optional</em>. Identifier of the message that will be replied to in the current chat, or in the chat
  *   <em>chat_id</em> if it is specified. Required if <em>ephemeral_message_id</em> isn't specified.
  * @param chatId
  *   <em>Optional</em>. If the message to be replied to is from a different chat, unique identifier for the chat or
  *   username of the bot, supergroup, or channel in the format <code>&#064;username</code>. Not supported for messages
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
  * @param quoteRichTexts
  *   <em>Optional</em>. A JSON-serialized list of quoted parts of the <a
  *   href="https://core.telegram.org/bots/api#richmessage">rich message</a> to be replied to. Must contain one element
  *   for each quoted text of the message in the order of their appearance. Each of the following is a separate quoted
  *   text: the text of a paragraph, a heading, a preformatted block, a footer, or a mathematical expression; the text
  *   and the credit of a block quotation or a pull quotation; the summary of a details block; the caption and each cell
  *   of a table; the text and the credit of a caption of a media block, a collage, a slideshow, or a map. Texts of
  *   blocks nested in lists, block quotations, details, collages, and slideshows are separate quoted texts too. The
  *   first and the last elements can contain only a part of the corresponding text; all other elements must contain the
  *   whole text. Formatting of the quoted parts must be kept, and rich texts must be nested in the same way as in the
  *   message received from the server. Mathematical expressions must be quoted entirely. Buttons in <a
  *   href="https://core.telegram.org/bots/api#richblockbuttons">RichBlockButtons</a>, anchors and dividers can't be
  *   quoted. Up to 32 quoted texts with total length up to 1024 characters can be specified. Only plain text, <a
  *   href="https://core.telegram.org/bots/api#richtextbold">RichTextBold</a>, <a
  *   href="https://core.telegram.org/bots/api#richtextitalic">RichTextItalic</a>, <a
  *   href="https://core.telegram.org/bots/api#richtextunderline">RichTextUnderline</a>, <a
  *   href="https://core.telegram.org/bots/api#richtextstrikethrough">RichTextStrikethrough</a>, <a
  *   href="https://core.telegram.org/bots/api#richtextspoiler">RichTextSpoiler</a>, <a
  *   href="https://core.telegram.org/bots/api#richtextsubscript">RichTextSubscript</a>, <a
  *   href="https://core.telegram.org/bots/api#richtextsuperscript">RichTextSuperscript</a>, <a
  *   href="https://core.telegram.org/bots/api#richtextdatetime">RichTextDateTime</a>, <a
  *   href="https://core.telegram.org/bots/api#richtextcustomemoji">RichTextCustomEmoji</a>, <a
  *   href="https://core.telegram.org/bots/api#richtextmathematicalexpression">RichTextMathematicalExpression</a>, and
  *   arrays of them are kept in the quote; other rich text types are replaced with their text. If non-empty, then
  *   <em>quote</em>, <em>quote_parse_mode</em> and <em>quote_entities</em> are ignored.
  * @param quotePosition
  *   <em>Optional</em>. Position of the quote in the original message in UTF-16 code units. For quotes from rich
  *   messages, it is the position in the text formed by joining plain texts of all quotable texts of the message,
  *   separated by line feeds.
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
  quoteRichTexts: List[RichText] = List.empty,
  quotePosition: Option[Int] = Option.empty,
  checklistTaskId: Option[Int] = Option.empty,
  pollOptionId: Option[String] = Option.empty
)
