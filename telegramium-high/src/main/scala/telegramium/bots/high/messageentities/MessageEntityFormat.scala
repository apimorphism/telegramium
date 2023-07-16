package telegramium.bots.high.messageentities

import telegramium.bots.{BoldMessageEntity, BotCommandMessageEntity, CashtagMessageEntity, CodeMessageEntity, EmailMessageEntity, HashtagMessageEntity, ItalicMessageEntity, MentionMessageEntity, MessageEntity, PhoneNumberMessageEntity, PreMessageEntity, StrikethroughMessageEntity, TextLinkMessageEntity, TextMentionMessageEntity, UnderlineMessageEntity, UrlMessageEntity, User}
import telegramium.bots.CustomEmojiMessageEntity
import telegramium.bots.SpoilerMessageEntity

/** Base class for different types of message entity formats that mirror the ones used by Telegram. Each format
  * corresponds to a specific type of content in a message, such as mentions, hashtags, URLs, and more.
  *
  * These formats can be used to wrap different parts of a message, which can then be composed to create a complete
  * message. The benefits of this approach:
  *
  *   - It automatically calculates the text offsets and lengths that are required by the Telegram Bot API.
  *   - It allows for generating the plain text of a message from the same source, simplifying the future message and
  *     formatting changes.
  *
  * Note: This lower-level API is not typically used directly. Instead, most users should work with the
  * `MessageEntities` API, which provides a higher-level, more user-friendly interface to the same functionality.
  *
  * @see
  *   https://core.telegram.org/bots/api#messageentity
  */
sealed abstract class MessageEntityFormat extends Product with Serializable {
  def text: String
}

object MessageEntityFormat {

  def toMessageEntities(entities: Vector[MessageEntityFormat]): List[MessageEntity] =
    entities
      .foldLeft((List.empty[MessageEntity], 0)) { case ((acc, offset), entity) =>
        def accumulate(me: MessageEntity) = (me :: acc, offset + me.length)

        entity match {
          case Plain(text) => (acc, offset + text.length)

          case Mention(text)    => accumulate(MentionMessageEntity(offset, text.length))
          case Cashtag(text)    => accumulate(CashtagMessageEntity(offset, text.length))
          case Code(text)       => accumulate(CodeMessageEntity(offset, text.length))
          case BotCommand(text) => accumulate(BotCommandMessageEntity(offset, text.length))
          case CustomEmoji(text, customEmojiId) =>
            accumulate(CustomEmojiMessageEntity(offset, text.length, customEmojiId))
          case Spoiler(text)           => accumulate(SpoilerMessageEntity(offset, text.length))
          case Email(text)             => accumulate(EmailMessageEntity(offset, text.length))
          case Bold(text)              => accumulate(BoldMessageEntity(offset, text.length))
          case Pre(text, language)     => accumulate(PreMessageEntity(offset, text.length, language))
          case Italic(text)            => accumulate(ItalicMessageEntity(offset, text.length))
          case Strikethrough(text)     => accumulate(StrikethroughMessageEntity(offset, text.length))
          case Underline(text)         => accumulate(UnderlineMessageEntity(offset, text.length))
          case Hashtag(text)           => accumulate(HashtagMessageEntity(offset, text.length))
          case TextMention(text, user) => accumulate(TextMentionMessageEntity(offset, text.length, user))
          case TextLink(text, url)     => accumulate(TextLinkMessageEntity(offset, text.length, url))
          case Url(text)               => accumulate(UrlMessageEntity(offset, text.length))
          case PhoneNumber(text)       => accumulate(PhoneNumberMessageEntity(offset, text.length))
        }
      }
      ._1
      .reverse

  final case class Plain(text: String) extends MessageEntityFormat

  object Plain {
    val lineBreak: Plain = Plain("\n")
  }

  final case class Mention(text: String) extends MessageEntityFormat

  final case class Cashtag(text: String) extends MessageEntityFormat

  final case class Code(text: String) extends MessageEntityFormat

  final case class BotCommand(text: String) extends MessageEntityFormat

  final case class CustomEmoji(text: String, customEmojiId: String) extends MessageEntityFormat

  final case class Spoiler(text: String) extends MessageEntityFormat

  final case class Email(text: String) extends MessageEntityFormat

  final case class Bold(text: String) extends MessageEntityFormat

  final case class Pre(text: String, language: Option[String]) extends MessageEntityFormat

  final case class Italic(text: String) extends MessageEntityFormat

  final case class Strikethrough(text: String) extends MessageEntityFormat

  final case class Underline(text: String) extends MessageEntityFormat

  final case class Hashtag(text: String) extends MessageEntityFormat

  final case class TextMention(text: String, user: User) extends MessageEntityFormat

  final case class TextLink(text: String, url: String) extends MessageEntityFormat

  final case class Url(text: String) extends MessageEntityFormat

  final case class PhoneNumber(text: String) extends MessageEntityFormat

  implicit class StringMessageEntityHelper(val sc: StringContext) extends AnyVal {
    def plain(args: Any*): Plain = Plain(build(args: _*))

    def cashtag(args: Any*): Cashtag = Cashtag(build(args: _*))

    def code(args: Any*): Code = Code(build(args: _*))

    def botCommand(args: Any*): BotCommand = BotCommand(build(args: _*))

    def spoiler(args: Any*): Spoiler = Spoiler(build(args: _*))

    def email(args: Any*): Email = Email(build(args: _*))

    def bold(args: Any*): Bold = Bold(build(args: _*))

    def italic(args: Any*): Italic = Italic(build(args: _*))

    def strikethrough(args: Any*): Strikethrough = Strikethrough(build(args: _*))

    def underline(args: Any*): Underline = Underline(build(args: _*))

    def hashtag(args: Any*): Hashtag = Hashtag(build(args: _*))

    def url(args: Any*): Url = Url(build(args: _*))

    def phoneNumber(args: Any*): PhoneNumber = PhoneNumber(build(args: _*))

    private def build(args: Any*): String = {
      val strings     = sc.parts.iterator
      val expressions = args.iterator
      val buf         = new java.lang.StringBuilder(strings.next())
      while (expressions.hasNext) {
        buf.append(expressions.next().toString)
        buf.append(strings.next())
      }
      buf.toString
    }

  }

}
