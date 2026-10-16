package telegramium.bots.high

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import scala.concurrent.duration.*

import cats.effect.IO
import cats.effect.unsafe.implicits.global

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import telegramium.bots.InputStreamFile

// These read the multipart part BotApi would send directly, without an HTTP client.
class InputStreamFilesSpec extends AnyFlatSpec with Matchers {

  private def part(file: InputStreamFile) =
    Http4sUtils.toStreamDataParts[IO](Map("document" -> file.file)).head

  "InputStreamFiles.fromStream" should "finalize the stream before the read returns, if the upload stops early" in {
    val finalized = new AtomicBoolean(false)
    // A slow finalizer, so this only passes if stopping the upload waits for it rather than just triggering it.
    val stream =
      fs2.Stream.constant[IO, Byte]('x'.toByte).onFinalize(IO.sleep(200.millis) >> IO(finalized.set(true)))

    InputStreamFiles.fromStream("endless.bin", stream).use(part(_).body.take(3).compile.drain).unsafeRunSync()

    finalized.get() shouldBe true
  }

  it should "run the stream again from the start on every upload attempt" in {
    val runs   = new AtomicInteger(0)
    val stream = fs2.Stream.eval(IO(runs.incrementAndGet())).flatMap(_ => fs2.Stream.emits("content".getBytes))

    val reads = InputStreamFiles
      .fromStream("content.bin", stream)
      .use(file => part(file).body.compile.to(Array).replicateA(2))
      .unsafeRunSync()

    runs.get() shouldBe 2
    reads.map(new String(_)) shouldBe List("content", "content")
  }

}
