package telegramium.bots.high.messageentities

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import telegramium.bots.*
import telegramium.bots.high.Methods
import telegramium.bots.high.messageentities.MessageEntityFormat.*
import telegramium.bots.high.messageentities.*

class MessageEntitiesSpec extends AnyFlatSpec with Matchers {

  private val user = User(1, isBot = false, firstName = "Test User")

  "MessageEntities" should "add an entity correctly" in {
    val entities = MessageEntities().add(Bold("test"))
    entities.toPlainText() shouldBe "test"
    entities.toTelegramEntities() shouldBe List(BoldMessageEntity(0, 4))
  }

  it should "add plain text correctly" in {
    val entities = MessageEntities().plain("test")
    entities.toPlainText() shouldBe "test"
    entities.toTelegramEntities() shouldBe List.empty
  }

  it should "add bold text correctly" in {
    val entities = MessageEntities().bold("test")
    entities.toPlainText() shouldBe "test"
    entities.toTelegramEntities() shouldBe List(BoldMessageEntity(0, 4))
  }

  it should "add a mention correctly" in {
    val entities = MessageEntities().mention("@test")
    entities.toPlainText() shouldBe "@test"
    entities.toTelegramEntities() shouldBe List(MentionMessageEntity(0, 5))
  }

  it should "add a cashtag correctly" in {
    val entities = MessageEntities().cashtag("$USD")
    entities.toPlainText() shouldBe "$USD"
    entities.toTelegramEntities() shouldBe List(CashtagMessageEntity(0, 4))
  }

  it should "add code correctly" in {
    val entities = MessageEntities().code("test")
    entities.toPlainText() shouldBe "test"
  }

  it should "add bot command correctly" in {
    val entities = MessageEntities().botCommand("/test")
    entities.toPlainText() shouldBe "/test"
    entities.toTelegramEntities() shouldBe List(BotCommandMessageEntity(0, 5))
  }

  it should "add custom emoji correctly" in {
    val entities = MessageEntities().customEmoji("test", "custom_emoji_id")
    entities.toPlainText() shouldBe "test"
    entities.toTelegramEntities() shouldBe List(CustomEmojiMessageEntity(0, 4, "custom_emoji_id"))
  }

  it should "add spoiler correctly" in {
    val entities = MessageEntities().spoiler("test")
    entities.toPlainText() shouldBe "test"
    entities.toTelegramEntities() shouldBe List(SpoilerMessageEntity(0, 4))
  }

  it should "add email correctly" in {
    val entities = MessageEntities().email("example@example.com")
    entities.toPlainText() shouldBe "example@example.com"
    entities.toTelegramEntities() shouldBe List(EmailMessageEntity(0, 19))
  }

  it should "add blockquote correctly" in {
    val entities = MessageEntities().blockquote("test")
    entities.toPlainText() shouldBe "test"
    entities.toTelegramEntities() shouldBe List(BlockquoteMessageEntity(0, 4))
  }

  it should "add expandable blockquote correctly" in {
    val entities = MessageEntities().expandableBlockquote("test")
    entities.toPlainText() shouldBe "test"
    entities.toTelegramEntities() shouldBe List(ExpandableBlockquoteMessageEntity(0, 4))
  }

  it should "add pre correctly" in {
    val entities = MessageEntities().pre("test", Some("scala"))
    entities.toPlainText() shouldBe "test"
    entities.toTelegramEntities() shouldBe List(PreMessageEntity(0, 4, Some("scala")))
  }

  it should "add italic text correctly" in {
    val entities = MessageEntities().italic("test")
    entities.toPlainText() shouldBe "test"
    entities.toTelegramEntities() shouldBe List(ItalicMessageEntity(0, 4))
  }

  it should "add strikethrough text correctly" in {
    val entities = MessageEntities().strikethrough("test")
    entities.toPlainText() shouldBe "test"
    entities.toTelegramEntities() shouldBe List(StrikethroughMessageEntity(0, 4))
  }

  it should "add underline text correctly" in {
    val entities = MessageEntities().underline("test")
    entities.toPlainText() shouldBe "test"
    entities.toTelegramEntities() shouldBe List(UnderlineMessageEntity(0, 4))
  }

  it should "add hashtag correctly" in {
    val entities = MessageEntities().hashtag("#test")
    entities.toPlainText() shouldBe "#test"
    entities.toTelegramEntities() shouldBe List(HashtagMessageEntity(0, 5))
  }

  it should "add text mention correctly" in {
    val entities = MessageEntities().textMention("test", user)
    entities.toPlainText() shouldBe "test"
    entities.toTelegramEntities() shouldBe List(TextMentionMessageEntity(0, 4, user))
  }

  it should "add text link correctly" in {
    val entities = MessageEntities().textLink("test", "https://test.com")
    entities.toPlainText() shouldBe "test"
    entities.toTelegramEntities() shouldBe List(TextLinkMessageEntity(0, 4, "https://test.com"))
  }

  it should "add url correctly" in {
    val entities = MessageEntities().url("https://test.com")
    entities.toPlainText() shouldBe "https://test.com"
    entities.toTelegramEntities() shouldBe List(UrlMessageEntity(0, 16))
  }

  it should "add phone number correctly" in {
    val entities = MessageEntities().phoneNumber("123456789")
    entities.toPlainText() shouldBe "123456789"
    entities.toTelegramEntities() shouldBe List(PhoneNumberMessageEntity(0, 9))
  }

  it should "convert to MessageEntities correctly" in {
    val entities = MessageEntities()
      .bold("bold")
      .plain(" ")
      .mention("@mention")
      .plain(" ")
      .cashtag("$USD")
      .plain(" ")
      .code("code")
      .plain(" ")
      .botCommand("/command")
      .plain(" ")
      .email("example@example.com")
      .plain(" ")
      .blockquote("blockquote")
      .plain(" ")
      .expandableBlockquote("expandableBlockquote")
      .plain(" ")
      .pre("pre", Some("scala"))
      .plain(" ")
      .italic("italic")
      .plain(" ")
      .strikethrough("strikethrough")
      .plain(" ")
      .underline("underline")
      .plain(" ")
      .hashtag("#hashtag")
      .plain(" ")
      .textMention("textMention", user)
      .plain(" ")
      .textLink("textLink", "https://example.com")
      .plain(" ")
      .url("https://example.com")
      .plain(" ")
      .phoneNumber("+12065550100")
      .lineBreak()

    entities
      .toPlainText() shouldBe "bold @mention $USD code /command example@example.com blockquote expandableBlockquote pre italic strikethrough underline #hashtag textMention textLink https://example.com +12065550100\n"

    val messageEntities = entities.toTelegramEntities()
    messageEntities.size should be(17)
    messageEntities.head should be(BoldMessageEntity(0, 4))
    messageEntities(1) should be(MentionMessageEntity(5, 8))
    messageEntities(2) should be(CashtagMessageEntity(14, 4))
    messageEntities(3) should be(CodeMessageEntity(19, 4))
    messageEntities(4) should be(BotCommandMessageEntity(24, 8))
    messageEntities(5) should be(EmailMessageEntity(33, 19))
    messageEntities(6) should be(BlockquoteMessageEntity(53, 10))
    messageEntities(7) should be(ExpandableBlockquoteMessageEntity(64, 20))
    messageEntities(8) should be(PreMessageEntity(85, 3, Some("scala")))
    messageEntities(9) should be(ItalicMessageEntity(89, 6))
    messageEntities(10) should be(StrikethroughMessageEntity(96, 13))
    messageEntities(11) should be(UnderlineMessageEntity(110, 9))
    messageEntities(12) should be(HashtagMessageEntity(120, 8))
    messageEntities(13) should be(TextMentionMessageEntity(129, 11, user))
    messageEntities(14) should be(TextLinkMessageEntity(141, 8, "https://example.com"))
    messageEntities(15) should be(UrlMessageEntity(150, 19))
    messageEntities(16) should be(PhoneNumberMessageEntity(170, 12))
  }

}
