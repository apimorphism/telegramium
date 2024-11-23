package telegramium.bots

/** This object contains information about one answer option in a poll to be sent.
  *
  * @param text
  *   Option text, 1-100 characters
  * @param textParseMode
  *   Optional. Mode for parsing entities in the text. See formatting options for more details. Currently, only custom
  *   emoji entities are allowed
  * @param textEntities
  *   Optional. A JSON-serialized list of special entities that appear in the poll option text. It can be specified
  *   instead of text_parse_mode
  */
final case class InputPollOption(
  text: String,
  textParseMode: Option[ParseMode] = Option.empty,
  textEntities: List[iozhik.OpenEnum[MessageEntity]] = List.empty
)
