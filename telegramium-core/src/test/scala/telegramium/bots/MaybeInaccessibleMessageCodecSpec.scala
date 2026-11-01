package telegramium.bots

import io.circe.parser._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import telegramium.bots.CirceImplicits._

class MaybeInaccessibleMessageCodecSpec extends AnyFlatSpec with Matchers {

  private val chat = Chat(id = 1, `type` = "private")

  private def decodeMessage(json: String) = parse(json).flatMap(_.as[Message])

  // https://github.com/apimorphism/telegramium/issues/691
  "MaybeInaccessibleMessage decoder" should "decode a pinned_message whose date is above 2^31" in {
    val pinnedDate = Int.MaxValue.toLong + 1
    val json =
      s"""{
        "message_id": 2, "date": 1, "chat": {"id": 1, "type": "private"},
        "pinned_message": {"message_id": 1, "date": $pinnedDate, "chat": {"id": 1, "type": "private"}}
      }"""
    decodeMessage(json) shouldBe Right(
      Message(
        messageId = 2,
        date = 1,
        chat = chat,
        pinnedMessage = Some(Message(messageId = 1, date = pinnedDate, chat = chat))
      )
    )
  }

  it should "decode an InaccessibleMessage when date is 0" in {
    val json =
      """{
        "message_id": 2, "date": 1, "chat": {"id": 1, "type": "private"},
        "pinned_message": {"message_id": 1, "date": 0, "chat": {"id": 1, "type": "private"}}
      }"""
    decodeMessage(json) shouldBe Right(
      Message(
        messageId = 2,
        date = 1,
        chat = chat,
        pinnedMessage = Some(InaccessibleMessage(chat = chat, messageId = 1, date = 0))
      )
    )
  }
}
