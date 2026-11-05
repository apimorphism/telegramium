package telegramium.bots

import java.io.{File => JFile}
import java.nio.charset.StandardCharsets
import java.nio.file.Files

import io.circe.{Decoder, Encoder, Json}
import io.circe.parser.parse
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
    "sendMessage"           -> codec(methods.sendMessage(ChatIntId(0), "")),
    "sendPoll"              -> codec(methods.sendPoll(ChatIntId(0), "", options = List.empty))
  )

  private def fixtures(dir: String): List[JFile] =
    Option(getClass.getResource(s"/fixtures/$dir"))
      .map(url => new JFile(url.toURI).listFiles().toList.filter(_.getName.endsWith(".json")).sortBy(_.getName))
      .getOrElse(List.empty)

  private def read(file: JFile): Json =
    parse(new String(Files.readAllBytes(file.toPath), StandardCharsets.UTF_8))
      .fold(e => fail(e.getMessage), json => json)

  private def roundTrips[A](file: JFile, codec: Codec[A]): Unit = {
    val json    = read(file)
    val decoded = json.as(codec.decoder).fold(e => fail(s"failed to decode: $e"), a => a)
    codec.encoder(decoded).as(codec.decoder) shouldBe Right(decoded)
  }

  private val updates   = fixtures("updates")
  private val responses = fixtures("responses")
  // hand-made, derived from the recorded ones: what a Bot API release newer than the library may send, named after
  // what each simulates
  private val future = fixtures("future")

  private def decode(file: JFile): Update = read(file).as[Update].fold(e => fail(s"failed to decode: $e"), u => u)

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
    (updates ++ responses).flatMap(f => ids("", read(f))).exists(id => math.abs(id) > Int.MaxValue) shouldBe true
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

  "unknown values decode as Unknown" - {
    "a reaction of an unknown type" in {
      decodeFuture("unknown_value-001.json").messageReaction.map(_.newReaction) shouldBe Some(
        List(iozhik.OpenEnum.Unknown[ReactionType]("future_reaction"))
      )
    }
    "an entity of an unknown type" in {
      decodeFuture("unknown_value-002.json").message.map(_.entities) shouldBe Some(
        List(iozhik.OpenEnum.Unknown[MessageEntity]("future_entity"))
      )
    }
  }

  "unknown fields nested inside known objects leave the known ones intact" in {
    val message = decodeFuture("unknown_nested_fields-001.json").message
    message.map(_.chat.id) shouldBe Some(1003L)
    message.flatMap(_.from).map(_.id) shouldBe Some(1003L)
    message.map(_.entities) shouldBe Some(List(iozhik.OpenEnum.Known[MessageEntity](BotCommandMessageEntity(0, 6))))
  }

}
