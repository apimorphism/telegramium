package telegramium.bots

import io.circe.Json
import io.circe.parser.parse
import io.circe.syntax._
import iozhik.OpenEnum
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import telegramium.bots.CirceImplicits._

/** Where an Unknown value ends and a decoding failure begins: only a tag this version doesn't know gives an Unknown
  * value. Anything malformed fails, so an incompatible change isn't hidden as one.
  */
class UnknownValuesCodecSpec extends AnyFlatSpec with Matchers {

  private def json(s: String): Json = parse(s).fold(e => fail(s"bad JSON in the test: $e"), j => j)

  private def entity(s: String) = json(s).as[OpenEnum[MessageEntity]]

  "An open union" should "decode a known tag as Known" in {
    entity("""{"type": "bold", "offset": 0, "length": 4}""") shouldBe Right(OpenEnum.Known(BoldMessageEntity(0, 4)))
  }

  it should "decode a tag it doesn't know as Unknown, with the whole object" in {
    val raw = json("""{"type": "future_entity", "offset": 0, "length": 4, "future_field": [1, {"a": null}]}""")
    raw.as[OpenEnum[MessageEntity]] shouldBe Right(OpenEnum.Unknown("future_entity", raw))
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
    decoded shouldBe Right(OpenEnum.Known(BoldMessageEntity(0, 4)))
    decoded.map(_.asJson) shouldBe Right(json("""{"type": "bold", "offset": 0, "length": 4}"""))
  }

  it should "encode an Unknown value as its JSON, whatever that holds" in {
    val raw = json("""{"type": "future_entity", "nested": {"type": "bold", "list": [1.50, -0.0, "x"]}, "n": null}""")
    (OpenEnum.Unknown("future_entity", raw): OpenEnum[MessageEntity]).asJson.noSpaces shouldBe raw.noSpaces
  }

  "A rich text" should "fail on a known tag whose object is malformed, nested ones too" in {
    json("""{"type": "bold"}""").as[OpenEnum[RichText]].isLeft shouldBe true
    json("""{"type": "bold", "text": {"type": "italic"}}""").as[OpenEnum[RichText]].isLeft shouldBe true
    json("""["plain", {"type": "url", "text": "link"}]""").as[OpenEnum[RichText]].isLeft shouldBe true
  }

  it should "fail on JSON that is no rich text at all" in {
    json("5").as[OpenEnum[RichText]].isLeft shouldBe true
    json("""{"text": "no tag"}""").as[OpenEnum[RichText]].isLeft shouldBe true
    json("""{"type": 5, "text": "hi"}""").as[OpenEnum[RichText]].isLeft shouldBe true
  }

  "A quote" should "send back an entity this version doesn't know as Telegram sent it, next to plain ones" in {
    val unknown = json("""{"type": "future_entity", "offset": 6, "length": 4, "future_field": {"x": 1}}""")
    val quote   = ReplyParameters(
      messageId = Some(1),
      quote = Some("plain bold"),
      quoteEntities = List(BoldMessageEntity(0, 5), OpenEnum.Unknown("future_entity", unknown))
    )
    quote.asJson.hcursor.get[List[Json]]("quote_entities") shouldBe
      Right(List(json("""{"type": "bold", "offset": 0, "length": 5}"""), unknown))
  }

  "An OpenEnum" should "compare with a plain value through equalsKnown, which takes only the wrapped type" in {
    val entities: List[OpenEnum[MessageEntity]] =
      List(OpenEnum(BoldMessageEntity(0, 4)), OpenEnum.Unknown("future_entity", json("""{"type": "future_entity"}""")))
    entities.map(_.equalsKnown(BoldMessageEntity(0, 4))) shouldBe List(true, false)
    entities.exists(_.equalsKnown(ItalicMessageEntity(0, 4))) shouldBe false
    assertTypeError("""entities.head.equalsKnown("bold")""")
  }

  "An Unknown value" should "print on one line, with its JSON" in {
    OpenEnum.Unknown("future_entity", json("""{"type": "future_entity", "n": [1, 2]}""")).toString shouldBe
      """Unknown(future_entity,{"type":"future_entity","n":[1,2]})"""
  }
}
