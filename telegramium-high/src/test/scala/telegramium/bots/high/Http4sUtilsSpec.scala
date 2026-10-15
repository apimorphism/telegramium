package telegramium.bots.high

import java.io.ByteArrayInputStream

import cats.effect.IO
import cats.effect.unsafe.implicits.global

import org.http4s.headers.`Content-Disposition`
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.typelevel.ci.CIString

import telegramium.bots.NamedInputStream

class Http4sUtilsSpec extends AnyFlatSpec with Matchers {

  private def contentDisposition(part: org.http4s.multipart.Part[IO]) =
    part.headers.get[`Content-Disposition`]

  "Http4sUtils.toStreamDataParts" should "stream the exact bytes of the input stream, even across many chunks" in {
    val content = Array.tabulate(20000)(i => (i % 256).toByte) // bigger than the 8192 chunk size
    val named   = NamedInputStream("report.pdf", new ByteArrayInputStream(content))

    val parts = Http4sUtils.toStreamDataParts[IO](Map("document" -> named))
    parts should have size 1

    val part = parts.head
    contentDisposition(part).flatMap(_.parameters.get(CIString("name"))) shouldBe Some("document")
    contentDisposition(part).flatMap(_.parameters.get(CIString("filename"))) shouldBe Some("report.pdf")

    val bytes = part.body.compile.to(Array).unsafeRunSync()
    bytes shouldBe content
  }

  it should "build one independent part per field, keyed by the request field name" in {
    val thumbnail = NamedInputStream("thumb.jpg", new ByteArrayInputStream("THUMB".getBytes))
    val document  = NamedInputStream("doc.pdf", new ByteArrayInputStream("DOC".getBytes))

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
    val named = NamedInputStream("empty.bin", new ByteArrayInputStream(Array.emptyByteArray))

    val parts = Http4sUtils.toStreamDataParts[IO](Map("document" -> named))
    val bytes = parts.head.body.compile.to(Array).unsafeRunSync()
    bytes shouldBe Array.emptyByteArray
  }

}
