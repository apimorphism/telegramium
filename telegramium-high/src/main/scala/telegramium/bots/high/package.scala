package telegramium.bots

import telegramium.bots.client.Method

package object high {
  object implicits {
    implicit def methodOps[Res](method: Method[Res]): MethodSyntax[Res] = new MethodSyntax[Res](method)

    final class MethodSyntax[Res](private val method: Method[Res]) extends AnyVal {
      def exec[F[_]](implicit api: Api[F]): F[Res] = api.execute(method)
    }
  }
}
