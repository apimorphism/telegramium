package telegramium.bots

sealed trait RichText {}

/** A subscript text.
  *
  * @param text
  *   The text
  */
final case class RichTextSubscript(text: iozhik.OpenUnion[RichText]) extends RichText

/** A text with a phone number.
  *
  * @param text
  *   The text
  * @param phoneNumber
  *   The phone number
  */
final case class RichTextPhoneNumber(text: iozhik.OpenUnion[RichText], phoneNumber: String) extends RichText

/** A monowidth text.
  *
  * @param text
  *   The text
  */
final case class RichTextCode(text: iozhik.OpenUnion[RichText]) extends RichText

/** A link to an anchor.
  *
  * @param text
  *   The link text
  * @param anchorName
  *   The name of the anchor. If the name is empty, then the link brings back to the top of the message.
  */
final case class RichTextAnchorLink(text: iozhik.OpenUnion[RichText], anchorName: String) extends RichText

/** Concatenation of rich texts.
  *
  * @param texts
  *   The texts
  */
final case class RichTextConcat(texts: List[iozhik.OpenUnion[RichText]] = List.empty) extends RichText

/** A text covered by a spoiler.
  *
  * @param text
  *   The text
  */
final case class RichTextSpoiler(text: iozhik.OpenUnion[RichText]) extends RichText

/** A reference.
  *
  * @param text
  *   Text of the reference
  * @param name
  *   The name of the reference
  */
final case class RichTextReference(text: iozhik.OpenUnion[RichText], name: String) extends RichText

/** A mention by a username.
  *
  * @param text
  *   The text
  * @param username
  *   The username
  */
final case class RichTextMention(text: iozhik.OpenUnion[RichText], username: String) extends RichText

/** An underlined text.
  *
  * @param text
  *   The text
  */
final case class RichTextUnderline(text: iozhik.OpenUnion[RichText]) extends RichText

/** A text with a link.
  *
  * @param text
  *   The text
  * @param url
  *   URL of the link
  */
final case class RichTextUrl(text: iozhik.OpenUnion[RichText], url: String) extends RichText

/** A button.
  *
  * @param button
  *   The button
  */
final case class RichTextButton(button: RichMessageButton) extends RichText

/** A bot command.
  *
  * @param text
  *   The text
  * @param botCommand
  *   The bot command
  */
final case class RichTextBotCommand(text: iozhik.OpenUnion[RichText], botCommand: String) extends RichText

/** A mention of a Telegram user by their identifier.
  *
  * @param text
  *   The text
  * @param user
  *   The mentioned user
  */
final case class RichTextTextMention(text: iozhik.OpenUnion[RichText], user: User) extends RichText

/** A mathematical expression.
  *
  * @param expression
  *   The expression in LaTeX format
  */
final case class RichTextMathematicalExpression(expression: String) extends RichText

/** A superscript text.
  *
  * @param text
  *   The text
  */
final case class RichTextSuperscript(text: iozhik.OpenUnion[RichText]) extends RichText

/** A text with an email address.
  *
  * @param text
  *   The text
  * @param emailAddress
  *   The email address
  */
final case class RichTextEmailAddress(text: iozhik.OpenUnion[RichText], emailAddress: String) extends RichText

/** A marked text.
  *
  * @param text
  *   The text
  */
final case class RichTextMarked(text: iozhik.OpenUnion[RichText]) extends RichText

/** Plain text.
  *
  * @param text
  *   The text
  */
final case class RichTextPlain(text: String) extends RichText

/** An italicized text.
  *
  * @param text
  *   The text
  */
final case class RichTextItalic(text: iozhik.OpenUnion[RichText]) extends RichText

/** A text with a bank card number.
  *
  * @param text
  *   The text
  * @param bankCardNumber
  *   The bank card number
  */
final case class RichTextBankCardNumber(text: iozhik.OpenUnion[RichText], bankCardNumber: String) extends RichText

/** A strikethrough text.
  *
  * @param text
  *   The text
  */
final case class RichTextStrikethrough(text: iozhik.OpenUnion[RichText]) extends RichText

/** A custom emoji.
  *
  * @param customEmojiId
  *   Unique identifier of the custom emoji. Use <a
  *   href="https://core.telegram.org/bots/api#getcustomemojistickers">getCustomEmojiStickers</a> to get full
  *   information about the sticker.
  * @param alternativeText
  *   Alternative emoji for the custom emoji
  */
final case class RichTextCustomEmoji(customEmojiId: String, alternativeText: String) extends RichText

/** An anchor.
  *
  * @param name
  *   The name of the anchor
  */
final case class RichTextAnchor(name: String) extends RichText

/** A hashtag.
  *
  * @param text
  *   The text
  * @param hashtag
  *   The hashtag
  */
final case class RichTextHashtag(text: iozhik.OpenUnion[RichText], hashtag: String) extends RichText

/** Formatted date and time.
  *
  * @param text
  *   The text
  * @param unixTime
  *   The Unix time associated with the entity
  * @param dateTimeFormat
  *   The string that defines the formatting of the date and time. See <a
  *   href="https://core.telegram.org/bots/api#date-time-entity-formatting">date-time entity formatting</a> for more
  *   details.
  */
final case class RichTextDateTime(text: iozhik.OpenUnion[RichText], unixTime: Long, dateTimeFormat: String)
    extends RichText

/** A cashtag.
  *
  * @param text
  *   The text
  * @param cashtag
  *   The cashtag
  */
final case class RichTextCashtag(text: iozhik.OpenUnion[RichText], cashtag: String) extends RichText

/** A bold text.
  *
  * @param text
  *   The text
  */
final case class RichTextBold(text: iozhik.OpenUnion[RichText]) extends RichText

/** An address in the TON blockchain.
  *
  * @param text
  *   Text of the address
  * @param address
  *   The address
  */
final case class RichTextTonAddress(text: iozhik.OpenUnion[RichText], address: String) extends RichText

/** A link to a reference.
  *
  * @param text
  *   The link text
  * @param referenceName
  *   The name of the reference
  */
final case class RichTextReferenceLink(text: iozhik.OpenUnion[RichText], referenceName: String) extends RichText
