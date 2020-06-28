package telegramium.bots.high

import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.traverse._
import cats.syntax.foldable._
import cats.syntax.applicativeError._
import cats.instances.int._
import cats.instances.list._
import cats.instances.option._
import cats.effect.{Sync, Timer}
import cats.effect.concurrent.Ref

import telegramium.bots._
import telegramium.bots.client._

import scala.util.control.NonFatal
import scala.concurrent.duration.{Duration, DurationInt, FiniteDuration}

abstract class LongPollBot[F[_]](bot: Api[F])(implicit syncF: Sync[F], timer: Timer[F]) extends Methods {

  import LongPollBot.OffsetKeeper

  private def noop[A](a: A) = syncF.pure(a).void

  def onMessage(msg: Message): F[Unit] = noop(msg)
  def onEditedMessage(msg: Message): F[Unit] = noop(msg)
  def onChannelPost(msg: Message): F[Unit] = noop(msg)
  def onEditedChannelPost(msg: Message): F[Unit] = noop(msg)
  def onInlineQuery(query: InlineQuery): F[Unit] = noop(query)
  def onCallbackQuery(query: CallbackQuery): F[Unit] = noop(query)
  def onChosenInlineResult(inlineResult: ChosenInlineResult): F[Unit] = noop(inlineResult)
  def onShippingQuery(query: ShippingQuery): F[Unit] = noop(query)
  def onPreCheckoutQuery(query: PreCheckoutQuery): F[Unit] = noop(query)
  def onPoll(poll: Poll): F[Unit] = noop(poll)
  def onPollAnswer(pollAnswer: PollAnswer): F[Unit] = noop(pollAnswer)

  def onUpdate(update: Update): F[Unit] = {
    for {
      _ <- update.message.fold(syncF.unit)(onMessage)
      _ <- update.editedMessage.fold(syncF.unit)(onEditedMessage)
      _ <- update.channelPost.fold(syncF.unit)(onChannelPost)
      _ <- update.editedChannelPost.fold(syncF.unit)(onEditedChannelPost)
      _ <- update.inlineQuery.fold(syncF.unit)(onInlineQuery)
      _ <- update.callbackQuery.fold(syncF.unit)(onCallbackQuery)
      _ <- update.chosenInlineResult.fold(syncF.unit)(onChosenInlineResult)
      _ <- update.shippingQuery.fold(syncF.unit)(onShippingQuery)
      _ <- update.preCheckoutQuery.fold(syncF.unit)(onPreCheckoutQuery)
      _ <- update.poll.fold(syncF.unit)(onPoll)
      _ <- update.pollAnswer.fold(syncF.unit)(onPollAnswer)
    } yield ()
  }

  def onError(e: Throwable): F[Unit] = {
    syncF.delay(e.printStackTrace())
  }

  def poll(offsetKeeper: OffsetKeeper[F]): F[Unit] = {
    for {
      offset <- offsetKeeper.getOffset
      seconds = pollInterval.toSeconds.toInt
      updates <- bot.execute(getUpdates(offset = Some(offset), timeout = Some(seconds)))
        .onError {
          case _: java.util.concurrent.TimeoutException => poll(offsetKeeper)
          case NonFatal(e) => for {
            _ <- onError(e)
            delay <- onErrorDelay
            _ <- timer.sleep(delay)
            _ <- poll(offsetKeeper)
          } yield ()
        }
      _ <- updates.traverse(onUpdate)
      _ <- updates.map(_.updateId).maximumOption.traverse(max => offsetKeeper.setOffset(max + 1))
      next <- poll(offsetKeeper)
    } yield {
      next
    }
  }

  def start(): F[Unit] = {
    for {
      _ <- bot.execute(deleteWebhook())
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

  /* Use effectful override to implement different backoff strategies */
  def onErrorDelay: F[FiniteDuration] = {
    syncF.delay(5.seconds)
  }
}

object LongPollBot {
  trait OffsetKeeper[F[_]] {
    def getOffset: F[Int]
    def setOffset(offset: Int): F[Unit]
  }
}
