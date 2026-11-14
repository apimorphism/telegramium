package telegramium.bots

import io.circe.Json
import io.circe.parser.parse
import io.circe.syntax._
import iozhik.{OpenEnum, OpenUnion}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import telegramium.bots.CirceImplicits._

/** Where an Unknown value ends and a decoding failure begins: only a tag or a string this version doesn't know gives an
  * Unknown value. Anything malformed fails, so an incompatible change isn't hidden as one.
  */
class UnknownValuesCodecSpec extends AnyFlatSpec with Matchers {

  private def json(s: String): Json = parse(s).fold(e => fail(s"bad JSON in the test: $e"), j => j)

  private def entity(s: String) = json(s).as[OpenUnion[MessageEntity]]

  "An open union" should "decode a known tag as Known" in {
    entity("""{"type": "bold", "offset": 0, "length": 4}""") shouldBe Right(OpenUnion.Known(BoldMessageEntity(0, 4)))
  }

  it should "decode a tag it doesn't know as Unknown, with the whole object" in {
    val raw = json("""{"type": "future_entity", "offset": 0, "length": 4, "future_field": [1, {"a": null}]}""")
    raw.as[OpenUnion[MessageEntity]] shouldBe Right(OpenUnion.Unknown("future_entity", raw))
  }

  it should "fail on a known tag whose object is malformed, not give an Unknown value" in {
    entity("""{"type": "bold", "length": 4}""").isLeft shouldBe true
    entity("""{"type": "bold", "offset": true, "length": 4}""").isLeft shouldBe true
    entity("""{"type": "text_link", "offset": 0, "length": 4}""").isLeft shouldBe true
  }

  it should "fail without a tag, or with one that isn't a string" in {
    entity("""{"offset": 0, "length": 4}""").isLeft shouldBe true
    entity("""{"type": 5, "offset": 0, "length": 4}""").isLeft shouldBe true
    entity("""{"type": null, "offset": 0, "length": 4}""").isLeft shouldBe true
    entity(""""bold"""").isLeft shouldBe true
  }

  it should "fail the value holding it when a known variant is malformed" in {
    json("""{"text": "quote", "position": 0, "entities": [{"type": "bold", "length": 4}]}""")
      .as[TextQuote]
      .isLeft shouldBe true
  }

  it should "drop a field it doesn't know from a known variant" in {
    val decoded = entity("""{"type": "bold", "offset": 0, "length": 4, "future_field": true}""")
    decoded shouldBe Right(OpenUnion.Known(BoldMessageEntity(0, 4)))
    decoded.map(_.asJson) shouldBe Right(json("""{"type": "bold", "offset": 0, "length": 4}"""))
  }

  it should "encode an Unknown value as its JSON, whatever that holds" in {
    val raw = json("""{"type": "future_entity", "nested": {"type": "bold", "list": [1.50, -0.0, "x"]}, "n": null}""")
    (OpenUnion.Unknown("future_entity", raw): OpenUnion[MessageEntity]).asJson.noSpaces shouldBe raw.noSpaces
  }

  "A rich text" should "fail on a known tag whose object is malformed, nested ones too" in {
    json("""{"type": "bold"}""").as[OpenUnion[RichText]].isLeft shouldBe true
    json("""{"type": "bold", "text": {"type": "italic"}}""").as[OpenUnion[RichText]].isLeft shouldBe true
    json("""["plain", {"type": "url", "text": "link"}]""").as[OpenUnion[RichText]].isLeft shouldBe true
  }

  it should "fail on JSON that is no rich text at all" in {
    json("5").as[OpenUnion[RichText]].isLeft shouldBe true
    json("""{"text": "no tag"}""").as[OpenUnion[RichText]].isLeft shouldBe true
    json("""{"type": 5, "text": "hi"}""").as[OpenUnion[RichText]].isLeft shouldBe true
  }

  "An open enum" should "decode a string it knows as Known and one it doesn't as Unknown" in {
    json(""""private"""").as[OpenEnum[ChatType]] shouldBe Right(OpenEnum.Known(ChatTypePrivate))
    json(""""future_chat"""").as[OpenEnum[ChatType]] shouldBe Right(OpenEnum.Unknown("future_chat"))
    // an enum's strings are matched as they are
    json(""""Private"""").as[OpenEnum[ChatType]] shouldBe Right(OpenEnum.Unknown("Private"))
  }

  it should "fail on a value that isn't a string" in {
    json("5").as[OpenEnum[ChatType]].isLeft shouldBe true
    json("null").as[OpenEnum[ChatType]].isLeft shouldBe true
    json("""{"type": "private"}""").as[OpenEnum[ChatType]].isLeft shouldBe true
    json("""{"id": 1, "type": 5}""").as[Chat].isLeft shouldBe true
    json("""{"id": 1}""").as[Chat].isLeft shouldBe true
  }

  it should "encode a Known value like the plain enum, and an Unknown one as its string" in {
    (OpenEnum(ChatTypeChannel): OpenEnum[ChatType]).asJson shouldBe (ChatTypeChannel: ChatType).asJson
    (OpenEnum.Unknown("future_chat"): OpenEnum[ChatType]).asJson shouldBe Json.fromString("future_chat")
  }

  "A Method's arguments" should "take the plain enum, not a string or an OpenEnum" in {
    import telegramium.bots.client.Methods
    object methods extends Methods
    methods.sendDice(ChatIntId(1), emoji = Some(EmojiDarts)).payload.json.hcursor.get[String]("emoji") shouldBe
      Right("🎯")
    assertTypeError("""methods.sendDice(ChatIntId(1), emoji = Some("🎯"))""")
    assertTypeError("""methods.sendDice(ChatIntId(1), emoji = Some(OpenEnum(EmojiDarts)))""")
    assertTypeError("""methods.sendDice(ChatIntId(1), emoji = Some(OpenEnum.Unknown("🪀")))""")
  }
}
