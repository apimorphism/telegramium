package telegramium.bots

/** Describes a rich message to be sent. Exactly one of the fields html, markdown, or blocks must be used.
  *
  * @param blocks
  *   Optional. Content of the rich message to send described as a list of blocks
  * @param html
  *   Optional. Content of the rich message to send described using HTML formatting. See rich message formatting options
  *   for more details. Use media field to specify the media used in the message.
  * @param markdown
  *   Optional. Content of the rich message to send described using Markdown formatting. See rich message formatting
  *   options for more details. Use media field to specify the media used in the message.
  * @param media
  *   Optional. List of media that are specified in the markdown or html fields using tg://photo?id=, tg://video?id=,
  *   and tg://audio?id= links
  * @param isRtl
  *   Optional. Pass True if the rich message must be shown right-to-left
  * @param skipEntityDetection
  *   Optional. Pass True to skip automatic detection of entities (e.g., URLs, email addresses, username mentions,
  *   hashtags, cashtags, bot commands, or phone numbers) in the text
  */
final case class InputRichMessage(
  blocks: List[InputRichBlock] = List.empty,
  html: Option[String] = Option.empty,
  markdown: Option[String] = Option.empty,
  media: List[InputRichMessageMedia] = List.empty,
  isRtl: Option[Boolean] = Option.empty,
  skipEntityDetection: Option[Boolean] = Option.empty
)
