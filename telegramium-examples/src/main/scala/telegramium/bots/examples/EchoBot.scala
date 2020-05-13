package telegramium.bots.examples

import cats.effect.{ConcurrentEffect, Sync, Timer}
import telegramium.bots.client.Api
import telegramium.bots.high.{BotApiMethod, SendMessage, WebhookBot}

class EchoBot[F[_]: ConcurrentEffect](
  bot: Api[F],
  port: Int,
  url: String,
  path: String
)(implicit syncF: Sync[F], timer: Timer[F]) extends WebhookBot[F](bot, port, url, path) {

  import telegramium.bots._

  override def onMessage(msg: Message): F[BotApiMethod[_]] =
    Sync[F].pure(SendMessage(ChatIntId(msg.chat.id), text = msg.text.getOrElse("")))

}
