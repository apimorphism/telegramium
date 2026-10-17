package telegramium.bots.high

import fs2.Stream

import telegramium.bots.IFile
import telegramium.bots.InputLinkFile
import telegramium.bots.client.Method

/** A file whose content is streamed into the request, in the effect `F` of the API that sends it. */
final case class Upload[F[_]](filename: String, stream: Stream[F, Byte])

/** A request together with the [[Upload]]s its `attach://` file references point at. Build one with [[Uploads]] and
  * send it with [[UploadApi.execute]].
  */
final case class WithUploads[F[_], Res](method: Method[Res], uploads: Map[String, Upload[F]])

/** Creates `attach://` references for streams inside an [[Uploads]] block. */
final class Attach[F[_]] private[high] () {
  private var uploads = Vector.empty[(String, Upload[F])]
  private var closed  = false

  /** Records `stream` as an upload named `filename`, and returns a file referencing it, usable anywhere an `IFile` is
    * expected: a method's file parameter, or a file nested in e.g. `InputMediaDocument`.
    */
  def apply(filename: String, stream: Stream[F, Byte]): IFile = synchronized {
    if (closed) throw new IllegalStateException(s"attach('$filename', ...) was called after its Uploads block returned")
    val name = s"upload${uploads.size}"
    uploads :+= name -> Upload(filename, stream)
    InputLinkFile(s"attach://$name")
  }

  private[high] def close(): Map[String, Upload[F]] = synchronized {
    closed = true
    uploads.toMap
  }

}

/** Builds a request that uploads streamed files:
  * {{{
  * api.execute(Uploads[F] { attach =>
  *   Methods.sendDocument(chatId, attach("report.pdf", bytes))
  * })
  * }}}
  * Each stream is sent as its own multipart part, without buffering it, and the request refers to it with
  * `attach://<part name>`. Streams run when the request is sent, and again each time it's sent.
  */
object Uploads {

  def apply[F[_]]: Builder[F] = new Builder[F]

  final class Builder[F[_]] private[Uploads] () {

    def apply[Res](build: Attach[F] => Method[Res]): WithUploads[F, Res] = {
      val attach = new Attach[F]
      val method = build(attach)
      WithUploads(method, attach.close())
    }

  }

}
