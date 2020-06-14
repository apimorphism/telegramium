package telegramium.bots.examples

import cats.effect.{Blocker, ConcurrentEffect, ContextShift, Timer}
import cats.implicits._
import telegramium.bots._
import telegramium.bots.client.Api
import telegramium.bots.high.{BotApi, Method, Methods, WebhookBot}

class EchoBot[F[_]: ConcurrentEffect: ContextShift](
  bot: Api[F],
  port: Int,
  url: String,
  path: String,
  blocker: Blocker
)(implicit api: BotApi[F], timer: Timer[F]) extends WebhookBot[F](bot, port, url, path, blocker) with Methods {

  override def onMessage(msg: Message): F[Method[_]] =
    sendMessage(ChatIntId(msg.chat.id), text = msg.text.getOrElse("")).exec
      .map(_ => sendMessage(ChatIntId(msg.chat.id), text = msg.text.getOrElse("")))

}
