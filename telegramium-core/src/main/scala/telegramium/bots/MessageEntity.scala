package telegramium.bots

sealed trait MessageEntity {

  /** Offset in UTF-16 code units to the start of the entity */
  def offset: Int

  /** Length of the entity in UTF-16 code units */
  def length: Int
}

/** mention
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  */
final case class MentionMessageEntity(offset: Int, length: Int) extends MessageEntity

/** cashtag
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  */
final case class CashtagMessageEntity(offset: Int, length: Int) extends MessageEntity

/** code
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  */
final case class CodeMessageEntity(offset: Int, length: Int) extends MessageEntity

/** bot_command
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  */
final case class BotCommandMessageEntity(offset: Int, length: Int) extends MessageEntity

/** custom_emoji
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  * @param customEmojiId
  *   unique identifier of the custom emoji. Use getCustomEmojiStickers to get full information about the sticker
  */
final case class CustomEmojiMessageEntity(offset: Int, length: Int, customEmojiId: String) extends MessageEntity

/** spoiler
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  */
final case class SpoilerMessageEntity(offset: Int, length: Int) extends MessageEntity

/** email
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  */
final case class EmailMessageEntity(offset: Int, length: Int) extends MessageEntity

/** blockquote
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  */
final case class BlockquoteMessageEntity(offset: Int, length: Int) extends MessageEntity

/** bold
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  */
final case class BoldMessageEntity(offset: Int, length: Int) extends MessageEntity

/** pre
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  * @param language
  *   Optional, the programming language of the entity text
  */
final case class PreMessageEntity(offset: Int, length: Int, language: Option[String] = Option.empty)
    extends MessageEntity

/** italic
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  */
final case class ItalicMessageEntity(offset: Int, length: Int) extends MessageEntity

/** strikethrough
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  */
final case class StrikethroughMessageEntity(offset: Int, length: Int) extends MessageEntity

/** underline
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  */
final case class UnderlineMessageEntity(offset: Int, length: Int) extends MessageEntity

/** hashtag
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  */
final case class HashtagMessageEntity(offset: Int, length: Int) extends MessageEntity

/** text_mention
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  * @param user
  *   the mentioned user
  */
final case class TextMentionMessageEntity(offset: Int, length: Int, user: User) extends MessageEntity

/** text_link
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  * @param url
  *   URL that will be opened after user taps on the text
  */
final case class TextLinkMessageEntity(offset: Int, length: Int, url: String) extends MessageEntity

/** url
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  */
final case class UrlMessageEntity(offset: Int, length: Int) extends MessageEntity

/** phone_number
  *
  * @param offset
  *   Offset in UTF-16 code units to the start of the entity
  * @param length
  *   Length of the entity in UTF-16 code units
  */
final case class PhoneNumberMessageEntity(offset: Int, length: Int) extends MessageEntity
