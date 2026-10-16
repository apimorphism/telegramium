package telegramium.bots

/** Describes a rich message to be sent. Exactly <strong>one</strong> of the fields <em>html</em>, <em>markdown</em>, or
  * <em>blocks</em> must be used.
  *
  * @param blocks
  *   <em>Optional</em>. Content of the rich message to send described as a list of blocks
  * @param html
  *   <em>Optional</em>. Content of the rich message to send described using HTML formatting. See <a
  *   href="https://core.telegram.org/bots/api#rich-message-formatting-options">rich message formatting options</a> for
  *   more details. Use <em>media</em> field to specify the media used in the message.
  * @param markdown
  *   <em>Optional</em>. Content of the rich message to send described using Markdown formatting. See <a
  *   href="https://core.telegram.org/bots/api#rich-message-formatting-options">rich message formatting options</a> for
  *   more details. Use <em>media</em> field to specify the media used in the message.
  * @param media
  *   <em>Optional</em>. List of media that are specified in the <em>markdown</em> or <em>html</em> fields using
  *   <code>tg://photo?id=</code>, <code>tg://video?id=</code>, <code>tg://document?id=</code>, and
  *   <code>tg://audio?id=</code> links
  * @param isRtl
  *   <em>Optional</em>. Pass <em>True</em> if the rich message must be shown right-to-left
  * @param skipEntityDetection
  *   <em>Optional</em>. Pass <em>True</em> to skip automatic detection of entities (e.g., URLs, email addresses,
  *   username mentions, hashtags, cashtags, bot commands, or phone numbers) in the text
  */
final case class InputRichMessage(
  blocks: List[InputRichBlock] = List.empty,
  html: Option[String] = Option.empty,
  markdown: Option[String] = Option.empty,
  media: List[InputRichMessageMedia] = List.empty,
  isRtl: Option[Boolean] = Option.empty,
  skipEntityDetection: Option[Boolean] = Option.empty
)
