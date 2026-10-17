package telegramium.bots

import io.circe._
import io.circe.syntax._
import iozhik.UploadSource
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import telegramium.bots.CirceImplicits._

class IFileCodecSpec extends AnyFlatSpec with Matchers {

  private val source = new UploadSource { val filename = "report.pdf" }

  "InputStreamFile" should "encode as the source's filename, like InputPartFile encodes as the file's name" in {
    InputStreamFile(source).asJson shouldBe Json.fromString("report.pdf")
  }

  it should "encode the same way whether accessed as IFile or as InputStreamFile directly" in {
    val file: IFile = InputStreamFile(source)
    file.asJson shouldBe Json.fromString("report.pdf")
  }

  it should "fail to decode, since its content can't round-trip through JSON" in {
    Json.fromString("report.pdf").as[InputStreamFile].isLeft shouldBe true
  }

}
