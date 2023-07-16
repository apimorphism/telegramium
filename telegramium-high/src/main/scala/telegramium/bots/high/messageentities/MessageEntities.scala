package telegramium.bots.high.messageentities

import telegramium.bots.MessageEntity
import telegramium.bots.User
import telegramium.bots.high.messageentities.MessageEntityFormat.*

/** Utility class to work with styled messages using message entities (see
  * https://core.telegram.org/bots/api#messageentity).
  *
  * Compose your message's text using different formatting options like plain text, bold, italics, links, mentions,
  * hashtags and more. This class automatically takes care of calculating text offsets and lengths required by the
  * Telegram API.
  *
  * @example
  *   {{{
  *   val msgEntities = MessageEntities()
  *     .bold("Hello, ")
  *     .mention("@user")
  *     .plain("! Welcome to our ")
  *     .textLink("website", "https://example.com")
  *     .lineBreak()
  *     .plain("Enjoy your stay.")
  *
  *   Methods.sendMessage(
  *     chatId,
  *     // Hello, @user! Welcome to our website\nEnjoy your stay.
  *     text = msgEntities.toPlainText(),
  *     // List(BoldMessageEntity(0, 7), MentionMessageEntity(7, 5), TextLinkMessageEntity(29, 7, "https://example.com"))
  *     entities = msgEntities.toTelegramEntities()
  *   )
  *   }}}
  *
  * @param underlying
  *   This class is basically a thin wrapper over the `Vector` collection. It supports some of the `Vector` operations
  *   directly, such as `++`, `:+`, and `+:`. For additional collection methods not directly available through this
  *   class, you can access the underlying `Vector` instance.
  */
class MessageEntities(val underlying: Vector[MessageEntityFormat] = Vector.empty) extends AnyVal {
  def add(entity: MessageEntityFormat): MessageEntities = :+(entity)

  /** Converts the underlying message entity formats to a list of Telegram message entities.
    *
    * Note: There is no `Plain` message entity in Telegram, so you won't see it in the final list.
    */
  def toTelegramEntities(): List[MessageEntity] = MessageEntityFormat.toMessageEntities(underlying)

  /** Converts the underlying message entity formats to a plain text string. You can pass it as the `text` parameter of
    * the `sendMessage` Telegram Bot API method.
    */
  def toPlainText(): String = underlying.map(_.text).mkString

  def plain(text: String): MessageEntities                         = add(Plain(text))
  def bold(text: String): MessageEntities                          = add(Bold(text))
  def mention(text: String): MessageEntities                       = add(Mention(text))
  def cashtag(text: String): MessageEntities                       = add(Cashtag(text))
  def code(text: String): MessageEntities                          = add(Code(text))
  def botCommand(text: String): MessageEntities                    = add(BotCommand(text))
  def customEmoji(text: String, customEmojiId: String)             = add(CustomEmoji(text, customEmojiId))
  def spoiler(text: String)                                        = add(Spoiler(text))
  def email(text: String): MessageEntities                         = add(Email(text))
  def pre(text: String, language: Option[String]): MessageEntities = add(Pre(text, language))
  def italic(text: String): MessageEntities                        = add(Italic(text))
  def strikethrough(text: String): MessageEntities                 = add(Strikethrough(text))
  def underline(text: String): MessageEntities                     = add(Underline(text))
  def hashtag(text: String): MessageEntities                       = add(Hashtag(text))
  def textMention(text: String, user: User): MessageEntities       = add(TextMention(text, user))
  def textLink(text: String, url: String): MessageEntities         = add(TextLink(text, url))
  def url(text: String): MessageEntities                           = add(Url(text))
  def phoneNumber(text: String): MessageEntities                   = add(PhoneNumber(text))
  def lineBreak()                                                  = add(MessageEntityFormat.Plain.lineBreak)

  /** Alias for `lineBreak` */
  def br() = lineBreak()

  def ++(other: MessageEntities): MessageEntities      = new MessageEntities(underlying ++ other.underlying)
  def :+(entity: MessageEntityFormat): MessageEntities = new MessageEntities(underlying :+ entity)
  def +:(entity: MessageEntityFormat): MessageEntities = new MessageEntities(entity +: underlying)
}

object MessageEntities {
  def apply(): MessageEntities = new MessageEntities()

  def apply(entities: Seq[MessageEntityFormat]): MessageEntities = new MessageEntities(entities.toVector)

  def apply(first: MessageEntityFormat, rest: MessageEntityFormat*): MessageEntities =
    apply(first +: rest.toVector)

}
