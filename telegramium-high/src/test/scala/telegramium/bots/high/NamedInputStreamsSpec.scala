package telegramium.bots.high

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

import cats.effect.IO
import cats.effect.std.Dispatcher
import cats.effect.unsafe.implicits.global

import fs2.interop.reactivestreams.fromPublisher
import org.scalatest.concurrent.Eventually
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.Seconds
import org.scalatest.time.Span
import zio.Runtime
import zio.Unsafe
import zio.ZIO
import zio.interop.reactivestreams.*
import zio.stream.ZStream

// Each test reads the multipart part BotApi would send, but stops after a few bytes, the way an
// upload that fails or is canceled midway does.
class NamedInputStreamsSpec extends AnyFlatSpec with Matchers with Eventually {

  private def readPartially(dispatcher: Dispatcher[IO], stream: fs2.Stream[IO, Byte]): IO[Unit] = {
    val named = NamedInputStreams.fromStream("endless.bin", stream, dispatcher)
    Http4sUtils.toStreamDataParts[IO](Map("document" -> named)).head.body.take(3).compile.drain
  }

  "NamedInputStreams.fromStream" should "finalize an fs2.Stream before the upload completes, if the upload stops early" in {
    val finalized = new AtomicBoolean(false)
    val stream    = fs2.Stream.constant[IO, Byte]('x'.toByte).onFinalize(IO(finalized.set(true)))

    Dispatcher.sequential[IO].use(readPartially(_, stream)).unsafeRunSync()

    finalized.get() shouldBe true
  }

  it should "run the stream again from the start on every upload attempt" in {
    val runs   = new AtomicInteger(0)
    val stream = fs2.Stream.eval(IO(runs.incrementAndGet())).flatMap(_ => fs2.Stream.emits("content".getBytes))

    val reads = Dispatcher
      .sequential[IO]
      .use { dispatcher =>
        val named = NamedInputStreams.fromStream("content.bin", stream, dispatcher)
        val part  = Http4sUtils.toStreamDataParts[IO](Map("document" -> named)).head
        part.body.compile.to(Array).replicateA(2)
      }
      .unsafeRunSync()

    runs.get() shouldBe 2
    reads.map(new String(_)) shouldBe List("content", "content")
  }

  it should "finalize a ZIO ZStream bridged via Reactive Streams, if the upload stops early" in {
    val finalized = new AtomicBoolean(false)
    val zstream   = ZStream.repeat('x'.toByte).ensuring(ZIO.succeed(finalized.set(true)))

    Dispatcher
      .sequential[IO]
      .use { dispatcher =>
        IO.blocking(Unsafe.unsafe(implicit u => Runtime.default.unsafe.run(zstream.toPublisher).getOrThrowFiberFailure()))
          .flatMap(publisher => readPartially(dispatcher, fromPublisher[IO, Byte](publisher, bufferSize = 16)))
      }
      .unsafeRunSync()

    // Reactive Streams cancellation is only a signal: ZIO interrupts the producing fiber asynchronously.
    eventually(timeout(Span(5, Seconds)))(finalized.get() shouldBe true)
  }

}
