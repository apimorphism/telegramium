package telegramium.bots.examples

import io.circe.Json
import io.circe.parser.parse
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class FixtureScrubberSpec extends AnyFreeSpec with Matchers {

  private def json(raw: String): Json = parse(raw).toOption.get

  "replaces the letters and digits of names, usernames, phone numbers and text, keeping their shape" in {
    FixtureScrubber.scrub(
      List(json("""{"first_name": "Ann", "username": "ann_1", "phone_number": "+4917", "text": "Hi, Ann! 👋"}"""))
    ) shouldBe List(
      json("""{"first_name": "xxx", "username": "xxx_0", "phone_number": "+0000", "text": "xx, xxx! 👋"}""")
    )
  }

  "replaces letters outside the Basic Multilingual Plane too, keeping the length in UTF-16 units" in {
    FixtureScrubber.scrub(List(json("""{"first_name": "𝓙𝓸𝓱𝓷 7"}"""))) shouldBe
      List(json("""{"first_name": "xxxxxxxx 0"}"""))
  }

  "keeps the indices and enum values whose keys end in _id" in {
    val doc = json(
      """{"accent_color_id": 1, "profile_accent_color_id": 5, "correct_option_id": 2, "correct_option_ids": [0, 3],
        |"option_ids": [1]}""".stripMargin
    )
    FixtureScrubber.scrub(List(doc)) shouldBe List(doc)
  }

  "keeps the strings decoders dispatch on, and other values that aren't personal" in {
    val doc = json(
      """{"chat": {"type": "private"}, "chat_type": "sender", "status": "member", "source": "gift_code", "mime_type": "image/png",
        |"emoji": "👍", "currency": "XTR", "date": 1700000000, "is_bot": false}""".stripMargin
    )
    FixtureScrubber.scrub(List(doc)) shouldBe List(doc)
  }

  "remaps integer IDs consistently across documents, keeping IDs above 2³¹ above it and channel IDs -100-prefixed" in {
    FixtureScrubber.scrub(
      List(
        json("""{"update_id": 5, "message": {"from": {"id": 7123456789}, "chat": {"id": -1001987654321}}}"""),
        json("""{"chat_id": -1001987654321, "user_ids": [7123456789, 42], "message_thread_id": -987}""")
      )
    ) shouldBe List(
      json("""{"update_id": 1001, "message": {"from": {"id": 7000000002}, "chat": {"id": -1000000000003}}}"""),
      json("""{"chat_id": -1000000000003, "user_ids": [7000000002, 1004], "message_thread_id": -1005}""")
    )
  }

  "replaces string IDs with fakes of the same shape, consistently across documents" in {
    val fileId   = "AgACAgIAAxkBAAIBZ2V_x-9aB3"
    val uniqueId = "AQADf8sxG3"
    val instance = "-1234567890123456789"
    val scrubbed = FixtureScrubber.scrub(
      List(
        json(s"""{"photo": [{"file_id": "$fileId", "file_unique_id": "$uniqueId"}], "chat_instance": "$instance"}"""),
        json(s"""{"file_id": "$fileId"}""")
      )
    )
    val List(first, second) = scrubbed.map(_.hcursor)
    val fakeFileId          = first.downField("photo").downArray.get[String]("file_id").toOption.get
    val fakeUniqueId        = first.downField("photo").downArray.get[String]("file_unique_id").toOption.get
    val fakeInstance        = first.get[String]("chat_instance").toOption.get

    def shape(s: String) = s.map(c => if (c.isDigit) '9' else if (c.isUpper) 'A' else if (c.isLower) 'a' else c)
    List(fileId -> fakeFileId, uniqueId -> fakeUniqueId, instance -> fakeInstance).foreach { case (real, fake) =>
      fake should not be real
      shape(fake) shouldBe shape(real)
    }
    second.get[String]("file_id") shouldBe Right(fakeFileId)
  }

  "moves locations to a fixed place" in {
    FixtureScrubber.scrub(
      List(json("""{"location": {"latitude": 48.137154, "longitude": 11.576124, "horizontal_accuracy": 25.5}}"""))
    ) shouldBe List(json("""{"location": {"latitude": 52.52, "longitude": 13.405, "horizontal_accuracy": 25.5}}"""))
  }

}
