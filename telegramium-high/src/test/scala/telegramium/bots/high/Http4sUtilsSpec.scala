package telegramium.bots.high

import java.util.concurrent.atomic.AtomicInteger

import cats.data.Kleisli
import cats.effect.IO
import cats.effect.unsafe.implicits.global

import iozhik.UploadSource
import org.http4s.headers.`Content-Disposition`
import org.http4s.multipart.Part
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.typelevel.ci.CIString

class Http4sUtilsSpec extends AnyFlatSpec with Matchers {

  private def parts(files: Map[String, UploadSource]): Vector[Part[IO]] =
    Http4sUtils.toStreamDataParts[IO](files).unsafeRunSync()

  private def param(part: Part[IO], name: String) =
    part.headers.get[`Content-Disposition`].flatMap(_.parameters.get(CIString(name)))

  private def bytes(part: Part[IO]) = part.body.compile.to(Array).unsafeRunSync()

  "Http4sUtils.toStreamDataParts" should "stream the exact bytes of the upload, even across many chunks" in {
    val content = Array.tabulate(20000)(i => (i % 256).toByte)
    val upload  = StreamUpload("report.pdf", fs2.Stream.emits(content).chunkN(4096).unchunks)

    val result = parts(Map("document" -> upload))
    result should have size 1
    param(result.head, "name") shouldBe Some("document")
    param(result.head, "filename") shouldBe Some("report.pdf")
    bytes(result.head) shouldBe content
  }

  it should "build one part per field, keyed by the request field name" in {
    val result = parts(
      Map(
        "thumbnail" -> StreamUpload("thumb.jpg", fs2.Stream.emits("THUMB".getBytes)),
        "document"  -> StreamUpload("doc.pdf", fs2.Stream.emits("DOC".getBytes))
      )
    )

    result.map(p => (param(p, "name"), param(p, "filename"), new String(bytes(p)))).toSet shouldBe Set(
      (Some("thumbnail"), Some("thumb.jpg"), "THUMB"),
      (Some("document"), Some("doc.pdf"), "DOC")
    )
  }

  it should "run the stream anew on every pull, so a retried request sends the whole content again" in {
    val runs   = new AtomicInteger(0)
    val upload =
      StreamUpload("report.pdf", fs2.Stream.eval(IO(runs.incrementAndGet())) >> fs2.Stream.emits("x".getBytes))
    val part = parts(Map("document" -> upload)).head

    bytes(part) shouldBe "x".getBytes
    bytes(part) shouldBe "x".getBytes
    runs.get() shouldBe 2
  }

  it should "lift the stream into an API effect other than IO" in {
    type K[A] = Kleisli[IO, Unit, A]
    val upload = StreamUpload("report.pdf", fs2.Stream.emits("CONTENT".getBytes))

    val read: K[Array[Byte]] = Http4sUtils
      .toStreamDataParts[K](Map("document" -> upload))
      .flatMap(parts => parts.head.body.compile.to(Array))
    val body = read.run(()).unsafeRunSync()

    new String(body) shouldBe "CONTENT"
  }

  it should "fail with UnsupportedUploadSource for a source it doesn't know" in {
    val custom = new UploadSource { val filename = "custom.bin" }

    val result = Http4sUtils.toStreamDataParts[IO](Map("document" -> custom)).attempt.unsafeRunSync()

    result.left.map(_.getClass) shouldBe Left(classOf[UnsupportedUploadSource])
  }

}
