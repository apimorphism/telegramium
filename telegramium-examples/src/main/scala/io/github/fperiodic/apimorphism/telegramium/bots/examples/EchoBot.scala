package io.github.fperiodic.apimorphism.telegramium.bots.examples

import cats.effect.Sync
import cats.effect.concurrent.Ref
import io.github.fperiodic.apimorphism.telegramium.bots.client.Api

class EchoBot[F[_]: Sync](bot: Api[F]) {

  import cats.syntax.flatMap._
  import cats.syntax.functor._
  import cats.syntax.foldable._
  import cats.syntax.traverse._
  import cats.instances.list._
  import cats.instances.option._
  import cats.instances.int._

  import io.github.fperiodic.apimorphism.telegramium.bots._
  import io.github.fperiodic.apimorphism.telegramium.bots.client._

  def loop(counter: Ref[F, Int]): F[Unit] = {
    for {
      count <- counter.get
      updates <- bot.getUpdates(GetUpdatesReq(offset = Some(count), timeout = Some(10)))
      _ <- updates.result.map(_.updateId).maximumOption.traverse(max => counter.set(max + 1))
      _ <- updates.result.flatMap(_.message).traverse{ m =>
        bot.sendMessage(SendMessageReq(
          chatId = ChatIntId(m.chat.id),
          text = m.text.getOrElse("NO_TEXT"),
        ))
      }
      next <- loop(counter)
    } yield {
      next
    }
  }

  def start(): F[Unit] = {
    for {
      counter <- Ref.of[F, Int](0)
      next <- loop(counter)
    } yield {
      next
    }
  }
}
