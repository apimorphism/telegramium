package telegramium.bots.high

import java.util.concurrent.atomic.AtomicInteger

import cats.effect.Async
import cats.effect.IO
import cats.effect.unsafe.implicits.global

import fs2.Stream
import io.circe.Json
import org.http4s.headers.`Content-Disposition`
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.typelevel.ci.CIString

import telegramium.bots.ChatIntId
import telegramium.bots.InputLinkFile
import telegramium.bots.InputMediaDocument
import telegramium.bots.Message

class UploadsSpec extends AnyFlatSpec with Matchers {

  private val bytes = Stream.emits("CONTENT".getBytes).covary[IO]

  private def field(json: Json, name: String): Option[Json] = json.asObject.flatMap(_(name))

  "Uploads" should "reference each stream with attach:// and a generated part name" in {
    var refs    = List.empty[Any]
    val request = Uploads[IO] { attach =>
      val document  = attach("report.pdf", bytes)
      val thumbnail = attach("thumb.jpg", bytes)
      refs = List(document, thumbnail)
      Methods.sendDocument(ChatIntId(0), document, thumbnail = Some(thumbnail))
    }

    refs shouldBe List(InputLinkFile("attach://upload0"), InputLinkFile("attach://upload1"))
    request.uploads.map { case (k, v) => k -> v.filename } shouldBe Map(
      "upload0" -> "report.pdf",
      "upload1" -> "thumb.jpg"
    )
    field(request.method.payload.json, "document") shouldBe Some(Json.fromString("attach://upload0"))
    field(request.method.payload.json, "thumbnail") shouldBe Some(Json.fromString("attach://upload1"))
    // The method only holds references, which BotApi sends as form fields; the streams go as their own parts.
    request.method.payload.files shouldBe Map(
      "document"  -> InputLinkFile("attach://upload0"),
      "thumbnail" -> InputLinkFile("attach://upload1")
    )
  }

  it should "work for files nested in other values, such as a media group" in {
    val request = Uploads[IO] { attach =>
      Methods.sendMediaGroup(
        ChatIntId(0),
        media = List(InputMediaDocument(attach("a.csv", bytes)), InputMediaDocument(attach("b.csv", bytes)))
      )
    }

    val media = field(request.method.payload.json, "media").flatMap(_.asArray).getOrElse(Vector.empty)
    media.flatMap(field(_, "media")) shouldBe Vector(
      Json.fromString("attach://upload0"),
      Json.fromString("attach://upload1")
    )
    request.uploads.keySet shouldBe Set("upload0", "upload1")
  }

  it should "reject attach calls made after its block returned" in {
    var leaked: Option[(String, Stream[IO, Byte]) => Any] = None
    Uploads[IO] { attach =>
      leaked = Some((name, stream) => attach(name, stream))
      Methods.sendMessage(ChatIntId(0), "hi")
    }

    an[IllegalStateException] should be thrownBy leaked.get("late.bin", bytes)
  }

  it should "type uploads by the caller's effect, in tagless-final code" in {
    // Compiles only because the stream's effect and the API's are the same F.
    def send[F[_]: Async](api: UploadApi[F], stream: Stream[F, Byte]): F[Message] =
      api.execute(Uploads[F](attach => Methods.sendDocument(ChatIntId(0), attach("report.pdf", stream))))

    succeed
  }

  "Http4sUtils.toUploadParts" should "send each upload as a part named after it, carrying its filename and stream" in {
    val runs   = new AtomicInteger(0)
    val stream = Stream.eval(IO(runs.incrementAndGet())) >> Stream.emits("CONTENT".getBytes)
    val part   = Http4sUtils.toUploadParts[IO](Map("upload0" -> Upload("report.pdf", stream))).head

    val disposition = part.headers.get[`Content-Disposition`]
    disposition.flatMap(_.parameters.get(CIString("name"))) shouldBe Some("upload0")
    disposition.flatMap(_.parameters.get(CIString("filename"))) shouldBe Some("report.pdf")
    // The stream runs when the body is read, and again on every read, so a resent request sends it all again.
    new String(part.body.compile.to(Array).unsafeRunSync()) shouldBe "CONTENT"
    new String(part.body.compile.to(Array).unsafeRunSync()) shouldBe "CONTENT"
    runs.get() shouldBe 2
  }

}
