package telegramium.bots.high

import io.circe.parser.parse
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import telegramium.bots.CirceImplicits.*
import telegramium.bots.Update

class UnknownUpdateFieldsSpec extends AnyFreeSpec with Matchers {

  private def unknownFields(raw: String) = {
    val json = parse(raw).toOption.get
    UnknownUpdateFields(json.as[Update].toOption.get, json)
  }

  "should not report fields of a known update type" in {
    unknownFields(
      """{"update_id": 1, "message": {"message_id": 0, "date": 0, "chat": {"id": 0, "type": "private"}, "text": "hi"}}"""
    ) shouldBe List.empty
  }

  "should ignore null fields" in {
    unknownFields("""{"update_id": 1, "message": null}""") shouldBe List.empty
  }

  "should report fields of an unknown update type" in {
    unknownFields("""{"update_id": 1, "some_future_update": {"id": 1}}""") shouldBe List("some_future_update")
  }

}
