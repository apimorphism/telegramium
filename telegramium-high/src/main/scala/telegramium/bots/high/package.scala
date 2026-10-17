package telegramium.bots

import telegramium.bots.client.Method

package object high {

  object implicits {
    implicit def methodOps[Res](method: Method[Res]): MethodSyntax[Res] = new MethodSyntax[Res](method)

    final class MethodSyntax[Res](private val method: Method[Res]) extends AnyVal {
      def exec[F[_]](implicit api: Api[F]): F[Res] = api.execute(method)
    }

    implicit def withUploadsOps[F[_], Res](request: WithUploads[F, Res]): WithUploadsSyntax[F, Res] =
      new WithUploadsSyntax[F, Res](request)

    final class WithUploadsSyntax[F[_], Res](private val request: WithUploads[F, Res]) extends AnyVal {
      def exec(implicit api: UploadApi[F]): F[Res] = api.execute(request)
    }

  }

}
