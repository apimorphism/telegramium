package telegramium.bots.high

import java.io.FilterInputStream

import cats.effect.Async
import cats.effect.std.Dispatcher

import fs2.Stream
import iozhik.NamedInputStream

object NamedInputStreams {

  /** Bridges an `fs2.Stream` into a [[iozhik.NamedInputStream]], for uploading it via `InputStreamFile`.
    *
    * Every upload attempt runs `stream` from the start. Once the upload finishes, fails or is canceled, the stream is
    * interrupted and its finalizers have run before the request completes.
    *
    * @param dispatcher
    *   used to run the stream from the plain `java.io.InputStream` the upload reads; it must stay open until the
    *   request using the returned value has completed
    */
  def fromStream[F[_]: Async](filename: String, stream: Stream[F, Byte], dispatcher: Dispatcher[F]): NamedInputStream =
    NamedInputStream(
      filename,
      () => {
        val (is, release) = dispatcher.unsafeRunSync(fs2.io.toInputStreamResource(stream).allocated)
        new FilterInputStream(is) {
          override def close(): Unit = dispatcher.unsafeRunSync(release)
        }
      }
    )

}
