package telegramium.bots.high

import java.io.FilterInputStream

import cats.effect.Async
import cats.effect.Resource
import cats.effect.std.Dispatcher

import fs2.Stream
import iozhik.NamedInputStream

import telegramium.bots.InputStreamFile

object InputStreamFiles {

  /** Bridges an `fs2.Stream` into an `InputStreamFile`, to upload it without buffering it in memory or on disk.
    *
    * Every upload attempt runs `stream` from the start. Once an upload stops reading it, whether it finished, failed or
    * was canceled, the stream is interrupted and its finalizers have run before the read returns.
    *
    * Use the file within the resource's scope: once the scope is closed, the stream can no longer be read or finalized.
    * Note that blaze-client keeps sending a canceled request's body in the background until the body ends, so with
    * blaze the stream may still be read after a canceled request has returned; ember-client stops reading it.
    */
  def fromStream[F[_]: Async](filename: String, stream: Stream[F, Byte]): Resource[F, InputStreamFile] =
    Dispatcher.parallel[F].map { dispatcher =>
      InputStreamFile(
        NamedInputStream(
          filename,
          () => {
            val (is, release) = dispatcher.unsafeRunSync(fs2.io.toInputStreamResource(stream).allocated)
            new FilterInputStream(is) {
              override def close(): Unit = dispatcher.unsafeRunSync(release)
            }
          }
        )
      )
    }

}
