package telegramium.bots.high

import LongPollBot.OffsetKeeper

import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.traverse._
import cats.syntax.foldable._
import cats.syntax.applicativeError._
import cats.instances.int._
import cats.instances.list._
import cats.instances.option._
import cats.effect.Sync
import cats.effect.concurrent.Ref
import scala.concurrent.duration.Duration
import scala.concurrent.duration.DurationInt

import telegramium.bots._
import telegramium.bots.client._

abstract class LongPollBot[F[_]](bot: Api[F])(implicit syncF: Sync[F]) {

  def onMessage(msg: Message): F[Unit] = syncF.delay(msg).void
  def onInlineQuery(query: InlineQuery): F[Unit] = syncF.delay(query).void
  def onCallbackQuery(query: CallbackQuery): F[Unit] = syncF.delay(query).void
  def onChosenInlineResult(inlineResult: ChosenInlineResult): F[Unit] = syncF.delay(inlineResult).void

  def onUpdate(update: Update): F[Unit] = {
    for {
      _ <- update.message.fold(syncF.unit)(onMessage)
      _ <- update.inlineQuery.fold(syncF.unit)(onInlineQuery)
      _ <- update.callbackQuery.fold(syncF.unit)(onCallbackQuery)
      _ <- update.chosenInlineResult.fold(syncF.unit)(onChosenInlineResult)
    } yield ()
  }

  def poll(offsetKeeper: OffsetKeeper[F]): F[Unit] = {
    for {
      offset <- offsetKeeper.getOffset
      seconds = pollInterval.toSeconds.toInt
      updates <- bot.getUpdates(GetUpdatesReq(offset = Some(offset), timeout = Some(seconds)))
        .onError {
          case _: java.util.concurrent.TimeoutException => poll(offsetKeeper)
        }
      _ <- updates.result.traverse(onUpdate)
      _ <- updates.result.map(_.updateId).maximumOption.traverse(max => offsetKeeper.setOffset(max + 1))
      next <- poll(offsetKeeper)
    } yield {
      next
    }
  }

  def start(): F[Unit] = {
    for {
      refCounter <- Ref.of[F, Int](0)
      offsetKeeper = new OffsetKeeper[F] {
        def getOffset: F[Int] = refCounter.get
        def setOffset(offset: Int): F[Unit] = refCounter.set(offset)
      }
      _ <- poll(offsetKeeper)
    } yield {
      ()
    }
  }

  def pollInterval: Duration = 10.seconds
}

object LongPollBot {
  trait OffsetKeeper[F[_]] {
    def getOffset: F[Int]
    def setOffset(offset: Int): F[Unit]
  }
}
