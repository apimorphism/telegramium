package telegramium.bots.high

import telegramium.bots.client.Method

trait Api[F[_]] {
  def execute[Res](method: Method[Res]): F[Res]
}

/** An [[Api]] that can also send requests with streamed [[Upload]]s. The uploads' effect is the API's, so sending
  * uploads built for another effect doesn't compile.
  */
trait UploadApi[F[_]] extends Api[F] {
  def execute[Res](request: WithUploads[F, Res]): F[Res]
}
