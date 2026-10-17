package telegramium.bots.high

import cats.effect.IO

import fs2.Stream
import iozhik.UploadSource

/** Content for an `InputStreamFile`, streamed into the request without buffering it in memory or on disk, e.g.
  * `InputStreamFile(StreamUpload("report.pdf", Files[IO].readAll(path)))`.
  *
  * `stream` is run anew for every request that sends it, and is lifted into the API's effect with `LiftIO`.
  */
final case class StreamUpload(filename: String, stream: Stream[IO, Byte]) extends UploadSource

/** Raised, before anything is sent, when an `InputStreamFile` holds an `UploadSource` other than [[StreamUpload]]. */
final class UnsupportedUploadSource(val source: UploadSource)
    extends IllegalArgumentException(
      s"Unsupported InputStreamFile source ${source.getClass.getName} (filename '${source.filename}'): " +
        "use telegramium.bots.high.StreamUpload"
    )
