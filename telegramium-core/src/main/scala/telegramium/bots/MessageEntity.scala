package telegramium.bots

sealed trait MessageEntity {

  /** Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
    * entity
    */
  def offset: Int

  /** Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
    */
  def length: Int
}

/** mention
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  */
final case class MentionMessageEntity(offset: Int, length: Int) extends MessageEntity

/** cashtag
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  */
final case class CashtagMessageEntity(offset: Int, length: Int) extends MessageEntity

/** code
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  */
final case class CodeMessageEntity(offset: Int, length: Int) extends MessageEntity

/** bot_command
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  */
final case class BotCommandMessageEntity(offset: Int, length: Int) extends MessageEntity

/** custom_emoji
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  * @param customEmojiId
  *   unique identifier of the custom emoji. Use <a
  *   href="https://core.telegram.org/bots/api#getcustomemojistickers">getCustomEmojiStickers</a> to get full
  *   information about the sticker.
  */
final case class CustomEmojiMessageEntity(offset: Int, length: Int, customEmojiId: String) extends MessageEntity

/** expandable_blockquote
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  */
final case class ExpandableBlockquoteMessageEntity(offset: Int, length: Int) extends MessageEntity

/** spoiler
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  */
final case class SpoilerMessageEntity(offset: Int, length: Int) extends MessageEntity

/** email
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  */
final case class EmailMessageEntity(offset: Int, length: Int) extends MessageEntity

/** blockquote
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  */
final case class BlockquoteMessageEntity(offset: Int, length: Int) extends MessageEntity

/** bold
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  */
final case class BoldMessageEntity(offset: Int, length: Int) extends MessageEntity

/** pre
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  * @param language
  *   Optional, the programming language of the entity text
  */
final case class PreMessageEntity(offset: Int, length: Int, language: Option[String] = Option.empty)
    extends MessageEntity

/** italic
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  */
final case class ItalicMessageEntity(offset: Int, length: Int) extends MessageEntity

/** date_time
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  * @param unixTime
  *   the Unix time associated with the entity
  * @param dateTimeFormat
  *   the string that defines the formatting of the date and time. See <a
  *   href="https://core.telegram.org/bots/api#date-time-entity-formatting">date-time entity formatting</a> for more
  *   details.
  */
final case class DateTimeMessageEntity(offset: Int, length: Int, unixTime: Long, dateTimeFormat: String)
    extends MessageEntity

/** strikethrough
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  */
final case class StrikethroughMessageEntity(offset: Int, length: Int) extends MessageEntity

/** underline
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  */
final case class UnderlineMessageEntity(offset: Int, length: Int) extends MessageEntity

/** hashtag
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  */
final case class HashtagMessageEntity(offset: Int, length: Int) extends MessageEntity

/** text_mention
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  * @param user
  *   the mentioned user
  */
final case class TextMentionMessageEntity(offset: Int, length: Int, user: User) extends MessageEntity

/** url
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  */
final case class UrlMessageEntity(offset: Int, length: Int) extends MessageEntity

/** text_link
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  * @param url
  *   URL that will be opened after user taps on the text
  */
final case class TextLinkMessageEntity(offset: Int, length: Int, url: String) extends MessageEntity

/** phone_number
  *
  * @param offset
  *   Offset in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a> to the start of the
  *   entity
  * @param length
  *   Length of the entity in <a href="https://core.telegram.org/api/entities#entity-length">UTF-16 code units</a>
  */
final case class PhoneNumberMessageEntity(offset: Int, length: Int) extends MessageEntity
