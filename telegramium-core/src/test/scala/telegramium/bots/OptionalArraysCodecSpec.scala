package telegramium.bots

import io.circe._
import io.circe.parser._
import io.circe.syntax._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import telegramium.bots.CirceImplicits._
import telegramium.bots.client.EditStoryReq
import telegramium.bots.client.GetUpdatesReq
import telegramium.bots.client.CirceImplicits._

class OptionalArraysCodecSpec extends AnyFlatSpec with Matchers {

  // https://github.com/apimorphism/telegramium/issues/686
  "An empty optional array" should "be left out of the encoded JSON" in {
    InputRichMessage(markdown = Some("**hi**")).asJson shouldBe Json.obj("markdown" -> "**hi**".asJson)
  }

  "A non-empty optional array" should "be encoded" in {
    val entities: List[MessageEntity] = List(BoldMessageEntity(offset = 0, length = 2))
    InputTextMessageContent("hi", entities = entities).asJson.hcursor.downField("entities").focus shouldBe
      Some(entities.asJson)
  }

  "An empty required array" should "still be encoded" in {
    InlineKeyboardMarkup(List.empty).asJson shouldBe Json.obj("inline_keyboard" -> Json.arr())
  }

  "allowed_updates" should "be left out when None, so Telegram keeps the previous setting" in {
    GetUpdatesReq().asJson.hcursor.downField("allowed_updates").focus shouldBe None
  }

  it should "be sent when an empty list, so Telegram resets it to the default" in {
    GetUpdatesReq(allowedUpdates = Some(List.empty)).asJson.hcursor.downField("allowed_updates").focus shouldBe
      Some(Json.arr())
  }

  private def editStoryAreas(areas: Option[List[StoryArea]]) =
    EditStoryReq("bc", 1, InputStoryContentPhoto(InputLinkFile("photo")), areas = areas).asJson.hcursor
      .downField("areas")
      .focus

  "editStory areas" should "be left out when None, so Telegram keeps the story's current areas" in {
    editStoryAreas(None) shouldBe None
  }

  it should "be sent when an empty list, so Telegram removes them" in {
    editStoryAreas(Some(List.empty)) shouldBe Some(Json.arr())
  }

  private def chatFullInfo(availableReactions: String) = parse(
    s"""{
      "id": 1, "type": "group", "accent_color_id": 0, "max_reaction_count": 11,
      "accepted_gift_types": {
        "unlimited_gifts": false, "limited_gifts": false, "unique_gifts": false,
        "premium_subscription": false, "gifts_from_channels": false
      }
      $availableReactions
    }"""
  ).flatMap(_.as[ChatFullInfo]).map(_.availableReactions)

  "ChatFullInfo.available_reactions" should "be None when omitted (all reactions allowed)" in {
    chatFullInfo("") shouldBe Right(None)
  }

  it should "be an empty list when empty (no reactions allowed)" in {
    chatFullInfo(""", "available_reactions": []""") shouldBe Right(Some(List.empty))
  }
}
