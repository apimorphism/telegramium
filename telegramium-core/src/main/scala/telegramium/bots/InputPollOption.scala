package telegramium.bots

/** This object contains information about one answer option in a poll to be sent.
  *
  * @param text
  *   Option text, 1-100 characters
  * @param textParseMode
  *   <em>Optional</em>. Mode for parsing entities in the text. See <a
  *   href="https://core.telegram.org/bots/api#formatting-options">formatting options</a> for more details. Currently,
  *   only custom emoji entities are allowed.
  * @param textEntities
  *   <em>Optional</em>. A JSON-serialized list of special entities that appear in the poll option text. It can be
  *   specified instead of <em>text_parse_mode</em>.
  * @param media
  *   <em>Optional</em>. Media added to the poll option
  */
final case class InputPollOption(
  text: String,
  textParseMode: Option[ParseMode] = Option.empty,
  textEntities: List[MessageEntity] = List.empty,
  media: Option[InputMedia] = Option.empty
)
