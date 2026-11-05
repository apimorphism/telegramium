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

}
