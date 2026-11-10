package telegramium.bots.high.messageentities

import org.scalacheck.Gen
import org.scalacheck.rng.Seed
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import telegramium.bots.BankCardNumberMessageEntity
import telegramium.bots.BlockquoteMessageEntity
import telegramium.bots.BoldMessageEntity
import telegramium.bots.BotCommandMessageEntity
import telegramium.bots.CashtagMessageEntity
import telegramium.bots.CodeMessageEntity
import telegramium.bots.CustomEmojiMessageEntity
import telegramium.bots.DateTimeMessageEntity
import telegramium.bots.EmailMessageEntity
import telegramium.bots.ExpandableBlockquoteMessageEntity
import telegramium.bots.GenPrims
import telegramium.bots.HashtagMessageEntity
import telegramium.bots.ItalicMessageEntity
import telegramium.bots.MentionMessageEntity
import telegramium.bots.MessageEntity
import telegramium.bots.PhoneNumberMessageEntity
import telegramium.bots.PreMessageEntity
import telegramium.bots.RoundTrips
import telegramium.bots.SpoilerMessageEntity
import telegramium.bots.StrikethroughMessageEntity
import telegramium.bots.TextLinkMessageEntity
import telegramium.bots.TextMentionMessageEntity
import telegramium.bots.TonAddressMessageEntity
import telegramium.bots.UnderlineMessageEntity
import telegramium.bots.UrlMessageEntity
import telegramium.bots.high.messageentities.MessageEntityFormat.*

/** Every variant of the generated MessageEntity has a builder: XMessageEntity(offset, length, rest*) is built by the
  * MessageEntityFormat.X(text, rest*) case class and by MessageEntities().x(text, rest*). The variants come from the
  * generated round trip cases, so a variant added to the Bot API fails here until the builders, and builtBy, get it.
  */
class MessageEntityBuildersSpec extends AnyFreeSpec with Matchers {

  private val params = Gen.Parameters.default.withSize(GenPrims.Budget)

  // a sample of each variant; MessageEntity's own case, the sum of them, samples as one of the variants
  private val variants: List[MessageEntity] =
    RoundTrips.all
      .map(c => (c.name, c.gen.pureApply(params, Seed(0L))))
      .collect { case (name, e: MessageEntity) if e.getClass.getSimpleName == name => e }

  // the case classes' fields, offset and length first
  private def fields(entity: MessageEntity): List[Any] = entity.asInstanceOf[Product].productIterator.toList

  private val text = "abc"

  // Each variant's format and MessageEntities builder, which should build it from text. A match on the sealed
  // MessageEntity, so a new variant is a compile warning here, and a MatchError naming it in the tests. Use the
  // MessageEntities method, not MessageEntities(format): the tests only check that the method exists.
  private def builtBy(entity: MessageEntity): (MessageEntityFormat, MessageEntities) = entity match {
    case _: MentionMessageEntity              => (Mention(text), MessageEntities().mention(text))
    case _: CashtagMessageEntity              => (Cashtag(text), MessageEntities().cashtag(text))
    case _: CodeMessageEntity                 => (Code(text), MessageEntities().code(text))
    case _: BotCommandMessageEntity           => (BotCommand(text), MessageEntities().botCommand(text))
    case CustomEmojiMessageEntity(_, _, id)   => (CustomEmoji(text, id), MessageEntities().customEmoji(text, id))
    case _: ExpandableBlockquoteMessageEntity =>
      (ExpandableBlockquote(text), MessageEntities().expandableBlockquote(text))
    case _: EmailMessageEntity                  => (Email(text), MessageEntities().email(text))
    case _: SpoilerMessageEntity                => (Spoiler(text), MessageEntities().spoiler(text))
    case _: BlockquoteMessageEntity             => (Blockquote(text), MessageEntities().blockquote(text))
    case _: BoldMessageEntity                   => (Bold(text), MessageEntities().bold(text))
    case PreMessageEntity(_, _, language)       => (Pre(text, language), MessageEntities().pre(text, language))
    case _: ItalicMessageEntity                 => (Italic(text), MessageEntities().italic(text))
    case DateTimeMessageEntity(_, _, time, fmt) =>
      (DateTime(text, time, fmt), MessageEntities().dateTime(text, time, fmt))
    case _: StrikethroughMessageEntity        => (Strikethrough(text), MessageEntities().strikethrough(text))
    case _: UnderlineMessageEntity            => (Underline(text), MessageEntities().underline(text))
    case _: HashtagMessageEntity              => (Hashtag(text), MessageEntities().hashtag(text))
    case TextMentionMessageEntity(_, _, user) => (TextMention(text, user), MessageEntities().textMention(text, user))
    case TextLinkMessageEntity(_, _, url)     => (TextLink(text, url), MessageEntities().textLink(text, url))
    case _: UrlMessageEntity                  => (Url(text), MessageEntities().url(text))
    case _: TonAddressMessageEntity           => (TonAddress(text), MessageEntities().tonAddress(text))
    case _: BankCardNumberMessageEntity       => (BankCardNumber(text), MessageEntities().bankCardNumber(text))
    case _: PhoneNumberMessageEntity          => (PhoneNumber(text), MessageEntities().phoneNumber(text))
  }

  "MessageEntity has variants" in {
    variants should not be empty
  }

  variants.foreach { entity =>
    val name     = entity.getClass.getSimpleName.stripSuffix("MessageEntity")
    val expected = 0 :: text.length :: fields(entity).drop(2)

    def buildsEntity(built: List[MessageEntity]) = {
      built.map(_.getClass) shouldBe List(entity.getClass)
      built.map(fields) shouldBe List(expected)
    }

    name - {
      s"is built by MessageEntityFormat.$name" in {
        val (format, _) = builtBy(entity)
        format.text shouldBe text
        buildsEntity(MessageEntityFormat.toMessageEntities(Vector(format)))
      }

      val builder = name.head.toLower.toString + name.tail
      s"is built by MessageEntities().$builder" in {
        // by name only: builtBy can't make sure its case calls the method
        assert(
          classOf[MessageEntities].getMethods.exists(_.getName == builder),
          s"MessageEntities has no $builder method"
        )
        val (_, entities) = builtBy(entity)
        entities.toPlainText() shouldBe text
        buildsEntity(entities.toTelegramEntities())
      }
    }
  }

}
