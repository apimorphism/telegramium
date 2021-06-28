package telegramium.bots.high

import telegramium.bots.client.Method

trait Api[F[_]] {
  def execute[Res](method: Method[Res]): F[Res]
}
