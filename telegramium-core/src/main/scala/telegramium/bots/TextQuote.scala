package telegramium.bots

/** This object contains information about the quoted part of a message that is replied to by the given message.
  *
  * @param text
  *   Text of the quoted part of a message that is replied to by the given message
  * @param position
  *   Approximate quote position in the original message in UTF-16 code units as specified by the sender
  * @param entities
  *   Optional. Special entities that appear in the quote. Currently, only bold, italic, underline, strikethrough,
  *   spoiler, and custom_emoji entities are kept in quotes.
  * @param isManual
  *   Optional. True, if the quote was chosen manually by the message sender. Otherwise, the quote was added
  *   automatically by the server.
  */
final case class TextQuote(
  text: String,
  position: Int,
  entities: List[MessageEntity] = List.empty,
  isManual: Option[Boolean] = Option.empty
)
