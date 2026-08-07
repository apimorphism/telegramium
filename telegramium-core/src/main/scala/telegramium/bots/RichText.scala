package telegramium.bots

sealed trait RichText {}

/** A subscript text.
  *
  * @param text
  *   The text
  */
final case class RichTextSubscript(text: iozhik.OpenEnum[RichText]) extends RichText

/** A text with a phone number.
  *
  * @param text
  *   The text
  * @param phoneNumber
  *   The phone number
  */
final case class RichTextPhoneNumber(text: iozhik.OpenEnum[RichText], phoneNumber: String) extends RichText

/** A monowidth text.
  *
  * @param text
  *   The text
  */
final case class RichTextCode(text: iozhik.OpenEnum[RichText]) extends RichText

/** A link to an anchor.
  *
  * @param text
  *   The link text
  * @param anchorName
  *   The name of the anchor. If the name is empty, then the link brings back to the top of the message.
  */
final case class RichTextAnchorLink(text: iozhik.OpenEnum[RichText], anchorName: String) extends RichText

/** A text covered by a spoiler.
  *
  * @param text
  *   The text
  */
final case class RichTextSpoiler(text: iozhik.OpenEnum[RichText]) extends RichText

/** A reference.
  *
  * @param text
  *   Text of the reference
  * @param name
  *   The name of the reference
  */
final case class RichTextReference(text: iozhik.OpenEnum[RichText], name: String) extends RichText

/** A mention by a username.
  *
  * @param text
  *   The text
  * @param username
  *   The username
  */
final case class RichTextMention(text: iozhik.OpenEnum[RichText], username: String) extends RichText

/** An underlined text.
  *
  * @param text
  *   The text
  */
final case class RichTextUnderline(text: iozhik.OpenEnum[RichText]) extends RichText

/** A text with a link.
  *
  * @param text
  *   The text
  * @param url
  *   URL of the link
  */
final case class RichTextUrl(text: iozhik.OpenEnum[RichText], url: String) extends RichText

/** A bot command.
  *
  * @param text
  *   The text
  * @param botCommand
  *   The bot command
  */
final case class RichTextBotCommand(text: iozhik.OpenEnum[RichText], botCommand: String) extends RichText

/** A mention of a Telegram user by their identifier.
  *
  * @param text
  *   The text
  * @param user
  *   The mentioned user
  */
final case class RichTextTextMention(text: iozhik.OpenEnum[RichText], user: User) extends RichText

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
final case class RichTextSuperscript(text: iozhik.OpenEnum[RichText]) extends RichText

/** A text with an email address.
  *
  * @param text
  *   The text
  * @param emailAddress
  *   The email address
  */
final case class RichTextEmailAddress(text: iozhik.OpenEnum[RichText], emailAddress: String) extends RichText

/** A marked text.
  *
  * @param text
  *   The text
  */
final case class RichTextMarked(text: iozhik.OpenEnum[RichText]) extends RichText

/** An italicized text.
  *
  * @param text
  *   The text
  */
final case class RichTextItalic(text: iozhik.OpenEnum[RichText]) extends RichText

/** A text with a bank card number.
  *
  * @param text
  *   The text
  * @param bankCardNumber
  *   The bank card number
  */
final case class RichTextBankCardNumber(text: iozhik.OpenEnum[RichText], bankCardNumber: String) extends RichText

/** A strikethrough text.
  *
  * @param text
  *   The text
  */
final case class RichTextStrikethrough(text: iozhik.OpenEnum[RichText]) extends RichText

/** A custom emoji.
  *
  * @param customEmojiId
  *   Unique identifier of the custom emoji. Use getCustomEmojiStickers to get full information about the sticker.
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
final case class RichTextHashtag(text: iozhik.OpenEnum[RichText], hashtag: String) extends RichText

/** Formatted date and time.
  *
  * @param text
  *   The text
  * @param unixTime
  *   The Unix time associated with the entity
  * @param dateTimeFormat
  *   The string that defines the formatting of the date and time. See date-time entity formatting for more details.
  */
final case class RichTextDateTime(text: iozhik.OpenEnum[RichText], unixTime: Long, dateTimeFormat: String)
    extends RichText

/** A cashtag.
  *
  * @param text
  *   The text
  * @param cashtag
  *   The cashtag
  */
final case class RichTextCashtag(text: iozhik.OpenEnum[RichText], cashtag: String) extends RichText

/** A bold text.
  *
  * @param text
  *   The text
  */
final case class RichTextBold(text: iozhik.OpenEnum[RichText]) extends RichText

/** A link to a reference.
  *
  * @param text
  *   The link text
  * @param referenceName
  *   The name of the reference
  */
final case class RichTextReferenceLink(text: iozhik.OpenEnum[RichText], referenceName: String) extends RichText
