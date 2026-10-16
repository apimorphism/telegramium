package telegramium.bots.high

import java.io.ByteArrayInputStream

import cats.effect.IO
import cats.effect.unsafe.implicits.global

import iozhik.NamedInputStream
import org.http4s.headers.`Content-Disposition`
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.typelevel.ci.CIString

class Http4sUtilsSpec extends AnyFlatSpec with Matchers {

  private def contentDisposition(part: org.http4s.multipart.Part[IO]) =
    part.headers.get[`Content-Disposition`]

  "Http4sUtils.toStreamDataParts" should "stream the exact bytes of the input stream, even across many chunks" in {
    val content = Array.tabulate(20000)(i => (i % 256).toByte) // bigger than the 8192 chunk size
    val named   = NamedInputStream("report.pdf", () => new ByteArrayInputStream(content))

    val parts = Http4sUtils.toStreamDataParts[IO](Map("document" -> named))
    parts should have size 1

    val part = parts.head
    contentDisposition(part).flatMap(_.parameters.get(CIString("name"))) shouldBe Some("document")
    contentDisposition(part).flatMap(_.parameters.get(CIString("filename"))) shouldBe Some("report.pdf")

    val bytes = part.body.compile.to(Array).unsafeRunSync()
    bytes shouldBe content
  }

  it should "build one independent part per field, keyed by the request field name" in {
    val thumbnail = NamedInputStream("thumb.jpg", () => new ByteArrayInputStream("THUMB".getBytes))
    val document  = NamedInputStream("doc.pdf", () => new ByteArrayInputStream("DOC".getBytes))

    val parts = Http4sUtils.toStreamDataParts[IO](Map("thumbnail" -> thumbnail, "document" -> document))
    parts should have size 2

    val decoded = parts.map { part =>
      val name     = contentDisposition(part).flatMap(_.parameters.get(CIString("name")))
      val filename = contentDisposition(part).flatMap(_.parameters.get(CIString("filename")))
      val bytes    = new String(part.body.compile.to(Array).unsafeRunSync())
      (name, filename, bytes)
    }.toSet

    decoded shouldBe Set(
      (Some("thumbnail"), Some("thumb.jpg"), "THUMB"),
      (Some("document"), Some("doc.pdf"), "DOC")
    )
  }

  it should "produce an empty part for an empty stream, without raising" in {
    val named = NamedInputStream("empty.bin", () => new ByteArrayInputStream(Array.emptyByteArray))

    val parts = Http4sUtils.toStreamDataParts[IO](Map("document" -> named))
    val bytes = parts.head.body.compile.to(Array).unsafeRunSync()
    bytes shouldBe Array.emptyByteArray
  }

  it should "not open the stream until the part's body is actually pulled" in {
    val opened = new java.util.concurrent.atomic.AtomicBoolean(false)
    val named  = NamedInputStream(
      "report.pdf",
      () => {
        opened.set(true)
        new ByteArrayInputStream("content".getBytes)
      }
    )

    val parts = Http4sUtils.toStreamDataParts[IO](Map("document" -> named))
    opened.get() shouldBe false

    parts.head.body.compile.drain.unsafeRunSync()
    opened.get() shouldBe true
  }

  it should "close the stream once the part's body has been fully consumed" in {
    val closed = new java.util.concurrent.atomic.AtomicBoolean(false)
    val named  = NamedInputStream(
      "report.pdf",
      () =>
        new ByteArrayInputStream("content".getBytes) {
          override def close(): Unit = closed.set(true)
        }
    )

    val parts = Http4sUtils.toStreamDataParts[IO](Map("document" -> named))
    closed.get() shouldBe false

    parts.head.body.compile.drain.unsafeRunSync()
    closed.get() shouldBe true
  }

  it should "still close the stream if reading from it fails" in {
    val closed = new java.util.concurrent.atomic.AtomicBoolean(false)
    val boom   = new RuntimeException("read failed")
    val named  = NamedInputStream(
      "report.pdf",
      () =>
        new java.io.InputStream {
          override def read(): Int                                   = throw boom
          override def read(b: Array[Byte], off: Int, len: Int): Int = throw boom
          override def close(): Unit                                 = closed.set(true)
        }
    )

    val parts  = Http4sUtils.toStreamDataParts[IO](Map("document" -> named))
    val result = parts.head.body.compile.drain.attempt.unsafeRunSync()

    result.left.map(_.getMessage) shouldBe Left(boom.getMessage)
    closed.get() shouldBe true
  }

  it should "re-invoke openStream on every separate pull, so a retried request gets an independent stream" in {
    val openCount = new java.util.concurrent.atomic.AtomicInteger(0)
    val named     = NamedInputStream(
      "report.pdf",
      () => {
        openCount.incrementAndGet()
        new ByteArrayInputStream("content".getBytes)
      }
    )

    val parts = Http4sUtils.toStreamDataParts[IO](Map("document" -> named))

    val firstRead  = parts.head.body.compile.to(Array).unsafeRunSync()
    val secondRead = parts.head.body.compile.to(Array).unsafeRunSync()

    openCount.get() shouldBe 2
    firstRead shouldBe "content".getBytes
    secondRead shouldBe "content".getBytes
  }

}
