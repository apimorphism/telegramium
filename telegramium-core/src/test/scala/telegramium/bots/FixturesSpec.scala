package telegramium.bots

import java.io.{File => JFile}

import io.circe.syntax._
import io.circe.{Decoder, Encoder, Json}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import telegramium.bots.CirceImplicits._
import telegramium.bots.client.{Method, Methods}

/** Real Telegram JSON, recorded by the examples' CaptureBot and scrubbed by FixtureScrubber (see CONTRIBUTING.md), must
  * decode, and give the same value after decode → encode → decode. The JSON itself needn't come back byte for byte,
  * since decoders drop the fields they don't know.
  */
class FixturesSpec extends AnyFreeSpec with Matchers {

  private object methods extends Methods

  private final class Codec[A](val decoder: Decoder[A], val encoder: Encoder[A])

  // decoded as the Method's own result, the way BotApi decodes it
  private def codec[A](method: Method[A])(implicit encoder: Encoder[A]): Codec[A] = new Codec(method.decoder, encoder)

  // the responses CaptureBot records
  private val responseCodecs: Map[String, Codec[_]] = Map(
    "getMe"                 -> codec(methods.getMe()),
    "getChat"               -> codec(methods.getChat(ChatIntId(0))),
    "getChatAdministrators" -> codec(methods.getChatAdministrators(ChatIntId(0))),
    "getUserProfilePhotos"  -> codec(methods.getUserProfilePhotos(0)),
    "getFile"               -> codec(methods.getFile("")),
    "sendMessage"           -> codec(methods.sendMessage(chatId = Some(ChatIntId(0)), text = "")),
    "sendPoll"              -> codec(methods.sendPoll(ChatIntId(0), "", options = List.empty))
  )

  private def roundTrips[A](file: JFile, codec: Codec[A]): Unit = {
    val json    = Fixtures.read(file)
    val decoded = json.as(codec.decoder).fold(e => fail(s"failed to decode: $e"), a => a)
    codec.encoder(decoded).as(codec.decoder) shouldBe Right(decoded)
  }

  private val updates   = Fixtures.files("updates")
  private val responses = Fixtures.files("responses")
  // hand-made, derived from the recorded ones: what a Bot API release newer than the library may send, named after
  // what each simulates
  private val future = Fixtures.files("future")

  private def decode(file: JFile): Update =
    Fixtures.read(file).as[Update].fold(e => fail(s"failed to decode: $e"), u => u)

  private def decodeFuture(name: String): Update =
    decode(future.find(_.getName == name).getOrElse(fail(s"no future fixture $name")))

  "there are recorded updates and responses" in {
    updates should not be empty
    responses should not be empty
  }

  "some fixture IDs are above 2³¹, so that an ID typed Int fails to decode" in {
    def ids(key: String, json: Json): List[Long] =
      json.fold(
        List.empty,
        _ => List.empty,
        n => if (key == "id" || key.endsWith("_id")) n.toLong.toList else List.empty,
        _ => List.empty,
        _.toList.flatMap(ids(key, _)),
        _.toList.flatMap { case (k, v) => ids(k, v) }
      )
    (updates ++ responses)
      .flatMap(f => ids("", Fixtures.read(f)))
      .exists(id => math.abs(id) > Int.MaxValue) shouldBe true
  }

  "updates decode and round-trip" - {
    updates.foreach(file => file.getName in roundTrips(file, new Codec(Decoder[Update], Encoder[Update])))
  }

  "responses decode and round-trip" - {
    responses.foreach { file =>
      val method = file.getName.takeWhile(_ != '-')
      file.getName in roundTrips(file, responseCodecs.getOrElse(method, fail(s"no codec for $method responses")))
    }
  }

  "future updates decode" - {
    "there is one for each case" in {
      future.map(_.getName.takeWhile(_ != '-')).distinct should contain theSameElementsAs List(
        "unknown_top_level_field",
        "unknown_update_kind",
        "unknown_value",
        "unknown_nested_fields"
      )
    }
    future.foreach(file => file.getName in decode(file))
  }

  "unknown values of an open enum of objects decode as Unknown" - {
    "a reaction of an unknown type" in {
      decodeFuture("unknown_value-001.json").messageReaction.map(_.newReaction) shouldBe Some(
        List(
          iozhik.OpenEnum.Unknown(
            "future_reaction",
            Json.obj(
              "type"               -> Json.fromString("future_reaction"),
              "future_reaction_id" -> Json.fromString("xxxxxxxxxxxxxxxxxxx")
            )
          )
        )
      )
    }
    "an entity of an unknown type" in {
      decodeFuture("unknown_value-002.json").message.map(_.entities) shouldBe Some(
        List(
          iozhik.OpenEnum.Unknown(
            "future_entity",
            Json.obj(
              "offset" -> Json.fromInt(0),
              "length" -> Json.fromInt(6),
              "type"   -> Json.fromString("future_entity")
            )
          )
        )
      )
    }
  }

  // #723: a bot quotes what it received. Telegram wants the quoted texts back one for each, nested as received, so
  // they go back as Telegram sent them: unknown ones too, at the top or however deep.
  "a quoted rich text goes back as Telegram sent it, unknown parts included" in {
    val update   = future.find(_.getName == "unknown_value-003.json").map(Fixtures.read).getOrElse(fail("no fixture"))
    val received = update.hcursor.downField("message").downField("quote").downField("rich_texts").as[List[Json]]
    val quote    = decodeFuture("unknown_value-003.json").message.flatMap(_.quote).getOrElse(fail("no quote"))
    quote.richTexts.map {
      case iozhik.OpenEnum.Known(_)        => "known"
      case iozhik.OpenEnum.Unknown(tag, _) => tag
    } shouldBe List("known", "future_block")

    def sentQuote(quoteRichTexts: List[iozhik.OpenEnum[RichText]]) =
      methods
        .sendMessage(
          text = "",
          chatId = Some(ChatIntId(0)),
          replyParameters = Some(ReplyParameters(messageId = Some(1), quoteRichTexts = quoteRichTexts))
        )
        .payload
        .json
        .hcursor
        .downField("reply_parameters")
        .downField("quote_rich_texts")
        .as[List[Json]]
    sentQuote(quote.richTexts) shouldBe received
  }

  "a quote a bot writes takes plain rich texts" in {
    ReplyParameters(quoteRichTexts = List(RichTextPlain("a"), RichTextBold(RichTextPlain("b")))).quoteRichTexts shouldBe
      List(iozhik.OpenEnum.Known(RichTextPlain("a")), iozhik.OpenEnum.Known(RichTextBold(RichTextPlain("b"))))
  }

  "unknown fields nested inside known objects leave the known ones intact" in {
    val message = decodeFuture("unknown_nested_fields-001.json").message
    message.map(_.chat.id) shouldBe Some(1003L)
    message.flatMap(_.from).map(_.id) shouldBe Some(1003L)
    message.map(_.entities) shouldBe Some(List(iozhik.OpenEnum.Known[MessageEntity](BotCommandMessageEntity(0, 6))))
  }

}
