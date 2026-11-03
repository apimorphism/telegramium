package telegramium.bots

import io.circe._
import io.circe.syntax._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import telegramium.bots.CirceImplicits._
import telegramium.bots.client.GetMeReq
import telegramium.bots.client.CirceImplicits._

class VariantTagCodecSpec extends AnyFlatSpec with Matchers {

  // https://github.com/apimorphism/telegramium/issues/704
  "A field typed with a variant of a tagged union" should "send the variant's tag" in {
    InputRichBlockPhoto(InputMediaPhoto(InputLinkFile("file_id"))).asJson.hcursor
      .downField("photo")
      .get[String]("type") shouldBe Right("photo")
  }

  "A variant" should "encode the same on its own as through its union" in {
    val photo = InputMediaPhoto(InputLinkFile("file_id"), caption = Some("hi"))
    photo.asJson shouldBe (photo: InputMedia).asJson
    photo.asJson.hcursor.get[String]("type") shouldBe Right("photo")
  }

  "A variant without fields" should "encode as its tag" in {
    BotCommandScopeDefault.asJson shouldBe Json.obj("type" -> "default".asJson)
    (BotCommandScopeDefault: BotCommandScope).asJson shouldBe Json.obj("type" -> "default".asJson)
  }

  "A request without params" should "carry its method, like the others, so a webhook reply names it" in {
    GetMeReq.asJson shouldBe Json.obj("method" -> "getMe".asJson)
  }
}
