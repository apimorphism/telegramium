package telegramium.bots

import io.circe.parser._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import telegramium.bots.CirceImplicits._

class MessageGenerationStoppedCodecSpec extends AnyFlatSpec with Matchers {

  private val chat = Chat(id = 1, `type` = "private")

  private def decode(json: String) = parse(json).flatMap(_.as[MessageGenerationStopped])

  // The Bot API server parses draft_id as int64 (telegram-bot-api Client.cpp, process_send_message_draft_query)
  "MessageGenerationStopped decoder" should "decode a draft_id above 2^31" in {
    val draftId = Int.MaxValue.toLong + 1
    decode(s"""{"chat": {"id": 1, "type": "private"}, "draft_id": $draftId}""") shouldBe
      Right(MessageGenerationStopped(chat = chat, draftId = draftId))
  }

  // The server serializes draft_id of this update as a string (Client.cpp, td::to_string), though the docs say Integer
  it should "decode a draft_id sent as a string" in {
    decode("""{"chat": {"id": 1, "type": "private"}, "draft_id": "123"}""") shouldBe
      Right(MessageGenerationStopped(chat = chat, draftId = 123L))
  }
}
