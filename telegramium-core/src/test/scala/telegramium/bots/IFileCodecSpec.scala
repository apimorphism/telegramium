package telegramium.bots

import java.io.ByteArrayInputStream

import io.circe._
import io.circe.syntax._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import telegramium.bots.CirceImplicits._

class IFileCodecSpec extends AnyFlatSpec with Matchers {

  "InputStreamFile" should "encode as the stream's filename, like InputPartFile encodes as the file's name" in {
    val file = InputStreamFile(NamedInputStream("report.pdf", new ByteArrayInputStream(Array.emptyByteArray)))
    file.asJson shouldBe Json.fromString("report.pdf")
  }

  it should "encode the same way whether accessed as IFile or as InputStreamFile directly" in {
    val file: IFile = InputStreamFile(NamedInputStream("report.pdf", new ByteArrayInputStream(Array.emptyByteArray)))
    file.asJson shouldBe Json.fromString("report.pdf")
  }

  it should "fail to decode, since it carries a live stream that cannot round-trip through JSON" in {
    Json.fromString("report.pdf").as[InputStreamFile].isLeft shouldBe true
  }

}
