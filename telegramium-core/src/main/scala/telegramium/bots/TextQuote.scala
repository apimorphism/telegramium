package telegramium.bots

/** This object contains information about the quoted part of a message that is replied to by the given message.
  *
  * @param text
  *   Text of the quoted part of a message that is replied to by the given message
  * @param position
  *   Approximate quote position in the original message in UTF-16 code units as specified by the sender
  * @param entities
  *   <em>Optional</em>. Special entities that appear in the quote. Currently, only <em>bold</em>, <em>italic</em>,
  *   <em>underline</em>, <em>strikethrough</em>, <em>spoiler</em>, <em>custom_emoji</em>, and <em>date_time</em>
  *   entities are kept in quotes.
  * @param isManual
  *   <em>Optional</em>. <em>True</em>, if the quote was chosen manually by the message sender. Otherwise, the quote was
  *   added automatically by the server.
  */
final case class TextQuote(
  text: String,
  position: Int,
  entities: List[iozhik.OpenEnum[MessageEntity]] = List.empty,
  isManual: Option[Boolean] = Option.empty
)
