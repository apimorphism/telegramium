package telegramium.bots

import io.circe._
import io.circe.parser._
import io.circe.syntax._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import org.scalacheck.{Arbitrary, Gen}
import telegramium.bots.CirceImplicits._

class RichTextCodecSpec extends AnyFlatSpec with Matchers with ScalaCheckPropertyChecks {

  private def known(rt: RichText): iozhik.OpenEnum[RichText] = iozhik.OpenEnum.Known(rt)

  // --- Unit tests: decode from raw JSON ---

  "RichText decoder" should "decode a plain string" in {
    val json = Json.fromString("Hello, world!")
    val result = json.as[iozhik.OpenEnum[RichText]]
    result shouldBe Right(known(RichTextPlain("Hello, world!")))
  }

  it should "decode an empty string" in {
    val json = Json.fromString("")
    val result = json.as[iozhik.OpenEnum[RichText]]
    result shouldBe Right(known(RichTextPlain("")))
  }

  it should "decode a flat array" in {
    val json = parse("""["Hello, ", {"type": "bold", "text": "world"}, "!"]""").toOption.get
    val result = json.as[iozhik.OpenEnum[RichText]]
    result shouldBe Right(known(RichTextConcat(List(
      known(RichTextPlain("Hello, ")),
      known(RichTextBold(known(RichTextPlain("world")))),
      known(RichTextPlain("!"))
    ))))
  }

  it should "decode a nested array" in {
    val json = parse("""[
      "Start ",
      ["nested ", {"type": "bold", "text": "deep"}],
      " end"
    ]""").toOption.get
    val result = json.as[iozhik.OpenEnum[RichText]]
    result shouldBe Right(known(RichTextConcat(List(
      known(RichTextPlain("Start ")),
      known(RichTextConcat(List(
        known(RichTextPlain("nested ")),
        known(RichTextBold(known(RichTextPlain("deep"))))
      ))),
      known(RichTextPlain(" end"))
    ))))
  }

  it should "decode an empty array" in {
    val json = parse("[]").toOption.get
    val result = json.as[iozhik.OpenEnum[RichText]]
    result shouldBe Right(known(RichTextConcat(List.empty)))
  }

  it should "decode a typed object (bold)" in {
    val json = parse("""{"type": "bold", "text": "important"}""").toOption.get
    val result = json.as[iozhik.OpenEnum[RichText]]
    result shouldBe Right(known(RichTextBold(known(RichTextPlain("important")))))
  }

  it should "decode a typed object (url) with nested text" in {
    val json = parse("""{"type": "url", "text": ["Click ", {"type": "bold", "text": "here"}], "url": "https://example.com"}""").toOption.get
    val result = json.as[iozhik.OpenEnum[RichText]]
    result shouldBe Right(known(RichTextUrl(
      text = known(RichTextConcat(List(
        known(RichTextPlain("Click ")),
        known(RichTextBold(known(RichTextPlain("here"))))
      ))),
      url = "https://example.com"
    )))
  }

  it should "decode a mathematical_expression" in {
    val json = parse("""{"type": "mathematical_expression", "expression": "E = mc^2"}""").toOption.get
    val result = json.as[iozhik.OpenEnum[RichText]]
    result shouldBe Right(known(RichTextMathematicalExpression("E = mc^2")))
  }

  it should "decode a custom_emoji" in {
    val json = parse("""{"type": "custom_emoji", "custom_emoji_id": "5368324170671202286", "alternative_text": "👍"}""").toOption.get
    val result = json.as[iozhik.OpenEnum[RichText]]
    result shouldBe Right(known(RichTextCustomEmoji("5368324170671202286", "👍")))
  }

  it should "decode an anchor" in {
    val json = parse("""{"type": "anchor", "name": "section-1"}""").toOption.get
    val result = json.as[iozhik.OpenEnum[RichText]]
    result shouldBe Right(known(RichTextAnchor("section-1")))
  }

  it should "decode a date_time" in {
    val json = parse("""{"type": "date_time", "text": "tomorrow", "unix_time": 1720000000, "date_time_format": "dd MMM yyyy"}""").toOption.get
    val result = json.as[iozhik.OpenEnum[RichText]]
    result shouldBe Right(known(RichTextDateTime(
      text = known(RichTextPlain("tomorrow")),
      unixTime = 1720000000L,
      dateTimeFormat = "dd MMM yyyy"
    )))
  }

  it should "decode an unknown type as OpenEnum.Unknown" in {
    val json = parse("""{"type": "future_type", "text": "hello"}""").toOption.get
    val result = json.as[iozhik.OpenEnum[RichText]]
    result shouldBe Right(iozhik.OpenEnum.Unknown("future_type"))
  }

  // --- Unit tests: encode ---

  "RichText encoder" should "encode RichTextPlain as a JSON string" in {
    val rt: RichText = RichTextPlain("hello")
    rt.asJson shouldBe Json.fromString("hello")
  }

  it should "encode RichTextConcat as a JSON array" in {
    val rt: RichText = RichTextConcat(List(
      known(RichTextPlain("a")),
      known(RichTextBold(known(RichTextPlain("b"))))
    ))
    val expected = parse("""["a", {"type": "bold", "text": "b"}]""").toOption.get
    rt.asJson shouldBe expected
  }

  it should "encode RichTextConcat with empty list as empty array" in {
    val rt: RichText = RichTextConcat(List.empty)
    rt.asJson shouldBe Json.arr()
  }

  it should "encode RichTextBold as a typed object" in {
    val rt: RichText = RichTextBold(known(RichTextPlain("text")))
    val expected = parse("""{"type": "bold", "text": "text"}""").toOption.get
    rt.asJson shouldBe expected
  }

  it should "encode mathematical_expression" in {
    val rt: RichText = RichTextMathematicalExpression("x^2")
    val expected = parse("""{"type": "mathematical_expression", "expression": "x^2"}""").toOption.get
    rt.asJson shouldBe expected
  }

  it should "encode nested RichText (url with array text)" in {
    val rt: RichText = RichTextUrl(
      text = known(RichTextConcat(List(
        known(RichTextPlain("see ")),
        known(RichTextItalic(known(RichTextPlain("this"))))
      ))),
      url = "https://example.com"
    )
    val expected = parse("""{"type": "url", "text": ["see ", {"type": "italic", "text": "this"}], "url": "https://example.com"}""").toOption.get
    rt.asJson shouldBe expected
  }

  // --- Unit tests: round-trip ---

  "RichText round-trip" should "preserve RichTextPlain" in {
    val rt = RichTextPlain("test")
    val encoded = (rt: RichText).asJson
    encoded.as[iozhik.OpenEnum[RichText]] shouldBe Right(known(rt))
  }

  it should "preserve RichTextConcat" in {
    val rt = RichTextConcat(List(
      known(RichTextPlain("a")),
      known(RichTextBold(known(RichTextPlain("b")))),
      known(RichTextPlain("c"))
    ))
    val encoded = (rt: RichText).asJson
    encoded.as[iozhik.OpenEnum[RichText]] shouldBe Right(known(rt))
  }

  it should "preserve typed objects" in {
    val rt = RichTextUrl(known(RichTextPlain("click")), "https://example.com")
    val encoded = (rt: RichText).asJson
    encoded.as[iozhik.OpenEnum[RichText]] shouldBe Right(known(rt))
  }

  it should "preserve deeply nested structures" in {
    val rt = RichTextConcat(List(
      known(RichTextConcat(List(
        known(RichTextConcat(List(
          known(RichTextPlain("deep")),
          known(RichTextBold(known(RichTextPlain("ly nested"))))
        )))
      )))
    ))
    val encoded = (rt: RichText).asJson
    encoded.as[iozhik.OpenEnum[RichText]] shouldBe Right(known(rt))
  }

  // --- Property-based tests ---

  private val genPlainText: Gen[String] = Gen.alphaNumStr.map(_.take(20))

  private def genRichText(depth: Int): Gen[RichText] = {
    val leaf: Gen[RichText] = Gen.frequency(
      5 -> genPlainText.map(RichTextPlain.apply),
      1 -> genPlainText.map(RichTextMathematicalExpression.apply),
      1 -> genPlainText.map(RichTextAnchor.apply),
      1 -> Gen.zip(genPlainText, genPlainText).map { case (id, alt) => RichTextCustomEmoji(id, alt) }
    )

    if (depth <= 0) leaf
    else {
      val recursive = genRichText(depth - 1).map(known)
      Gen.frequency(
        4 -> leaf,
        2 -> recursive.map(RichTextBold.apply),
        2 -> recursive.map(RichTextItalic.apply),
        1 -> recursive.map(RichTextUnderline.apply),
        1 -> recursive.map(RichTextStrikethrough.apply),
        1 -> recursive.map(RichTextSpoiler.apply),
        1 -> recursive.map(RichTextCode.apply),
        1 -> recursive.map(RichTextSubscript.apply),
        1 -> recursive.map(RichTextSuperscript.apply),
        1 -> recursive.map(RichTextMarked.apply),
        2 -> Gen.zip(recursive, genPlainText).map { case (t, u) => RichTextUrl(t, u) },
        1 -> Gen.zip(recursive, genPlainText).map { case (t, e) => RichTextEmailAddress(t, e) },
        1 -> Gen.zip(recursive, genPlainText).map { case (t, p) => RichTextPhoneNumber(t, p) },
        1 -> Gen.zip(recursive, genPlainText).map { case (t, h) => RichTextHashtag(t, h) },
        1 -> Gen.zip(recursive, genPlainText).map { case (t, c) => RichTextCashtag(t, c) },
        1 -> Gen.zip(recursive, genPlainText).map { case (t, b) => RichTextBotCommand(t, b) },
        1 -> Gen.zip(recursive, genPlainText).map { case (t, a) => RichTextAnchorLink(t, a) },
        1 -> Gen.zip(recursive, genPlainText).map { case (t, r) => RichTextReference(t, r) },
        1 -> Gen.zip(recursive, genPlainText).map { case (t, r) => RichTextReferenceLink(t, r) },
        1 -> Gen.zip(recursive, genPlainText).map { case (t, m) => RichTextMention(t, m) },
        1 -> Gen.zip(recursive, genPlainText).map { case (t, b) => RichTextBankCardNumber(t, b) },
        1 -> Gen.zip(recursive, Gen.posNum[Long], genPlainText).map { case (t, u, f) => RichTextDateTime(t, u, f) },
        3 -> Gen.listOfN(3, genRichText(depth - 1).map(known)).map(RichTextConcat.apply)
      )
    }
  }

  private implicit val arbRichText: Arbitrary[RichText] = Arbitrary(genRichText(3))

  "RichText property-based" should "round-trip encode/decode for arbitrary RichText" in {
    forAll { (rt: RichText) =>
      val encoded = rt.asJson
      val decoded = encoded.as[iozhik.OpenEnum[RichText]]
      decoded shouldBe Right(known(rt))
    }
  }

  it should "always produce a JSON string for RichTextPlain" in {
    forAll(genPlainText) { text =>
      val rt: RichText = RichTextPlain(text)
      rt.asJson.isString shouldBe true
    }
  }

  it should "always produce a JSON array for RichTextConcat" in {
    forAll(Gen.listOf(genRichText(1).map(known))) { items =>
      val rt: RichText = RichTextConcat(items)
      rt.asJson.isArray shouldBe true
    }
  }

  it should "always produce a JSON object with type field for typed RichText" in {
    val genTyped = genRichText(2).suchThat {
      case _: RichTextPlain  => false
      case _: RichTextConcat => false
      case _                 => true
    }
    forAll(genTyped) { rt =>
      val json = rt.asJson
      json.isObject shouldBe true
      json.hcursor.get[String]("type").isRight shouldBe true
    }
  }

  // --- Golden file tests ---

  "RichText golden file" should "decode all examples from richtext-golden.json" in {
    val source = scala.io.Source.fromResource("richtext-golden.json")
    val content = try source.mkString finally source.close()
    val doc = parse(content).toOption.get.hcursor

    val cases = List(
      "plain_string",
      "flat_array",
      "nested_array",
      "typed_object_bold",
      "typed_object_with_nested_text",
      "paragraph_text",
      "mathematical_expression",
      "custom_emoji",
      "anchor",
      "date_time",
      "deeply_nested",
      "empty_array"
    )

    cases.foreach { name =>
      withClue(s"Failed to decode '$name': ") {
        val json = doc.downField(name).focus.get
        val result = json.as[iozhik.OpenEnum[RichText]]
        result.isRight shouldBe true
      }
    }
  }

  it should "decode and re-encode plain_string preserving wire format" in {
    val json = Json.fromString("Hello, world!")
    val decoded = json.as[iozhik.OpenEnum[RichText]].toOption.get
    decoded match {
      case iozhik.OpenEnum.Known(rt) => (rt: RichText).asJson shouldBe json
      case _                         => fail("Expected Known")
    }
  }

  it should "decode and re-encode flat_array preserving wire format" in {
    val json = parse("""["Hello, ", {"type": "bold", "text": "world"}, "!"]""").toOption.get
    val decoded = json.as[iozhik.OpenEnum[RichText]].toOption.get
    decoded match {
      case iozhik.OpenEnum.Known(rt) => (rt: RichText).asJson shouldBe json
      case _                         => fail("Expected Known")
    }
  }

  it should "decode and re-encode typed_object preserving wire format" in {
    val json = parse("""{"type": "bold", "text": "important"}""").toOption.get
    val decoded = json.as[iozhik.OpenEnum[RichText]].toOption.get
    decoded match {
      case iozhik.OpenEnum.Known(rt) => (rt: RichText).asJson shouldBe json
      case _                         => fail("Expected Known")
    }
  }
}
