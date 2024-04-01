package telegramium.bots.high

import scala.concurrent.duration.*
import scala.util.control.NonFatal

import cats.Monad
import cats.Parallel
import cats.effect.Async
import cats.effect.Ref
import cats.syntax.all.*

import telegramium.bots.*
import telegramium.bots.client.*

abstract class LongPollBot[F[_]: Parallel: Async](bot: Api[F]) extends Methods {

  import LongPollBot.OffsetKeeper

  private def noop[A](a: A) = Monad[F].pure(a).void

  def onMessage(msg: Message): F[Unit]                                      = noop(msg)
  def onEditedMessage(msg: Message): F[Unit]                                = noop(msg)
  def onChannelPost(msg: Message): F[Unit]                                  = noop(msg)
  def onEditedChannelPost(msg: Message): F[Unit]                            = noop(msg)
  def onBusinessConnection(connection: BusinessConnection): F[Unit]         = noop(connection)
  def onBusinessMessage(msg: Message): F[Unit]                              = noop(msg)
  def onEditedBusinessMessage(msg: Message): F[Unit]                        = noop(msg)
  def onDeletedBusinessMessages(messages: BusinessMessagesDeleted): F[Unit] = noop(messages)
  def onMessageReaction(reaction: MessageReactionUpdated): F[Unit]          = noop(reaction)
  def onMessageReactionCount(count: MessageReactionCountUpdated): F[Unit]   = noop(count)
  def onInlineQuery(query: InlineQuery): F[Unit]                            = noop(query)
  def onCallbackQuery(query: CallbackQuery): F[Unit]                        = noop(query)
  def onChosenInlineResult(inlineResult: ChosenInlineResult): F[Unit]       = noop(inlineResult)
  def onShippingQuery(query: ShippingQuery): F[Unit]                        = noop(query)
  def onPreCheckoutQuery(query: PreCheckoutQuery): F[Unit]                  = noop(query)
  def onPoll(poll: Poll): F[Unit]                                           = noop(poll)
  def onPollAnswer(pollAnswer: PollAnswer): F[Unit]                         = noop(pollAnswer)
  def onMyChatMember(myChatMember: ChatMemberUpdated): F[Unit]              = noop(myChatMember)
  def onChatMember(chatMember: ChatMemberUpdated): F[Unit]                  = noop(chatMember)
  def onChatJoinRequest(request: ChatJoinRequest): F[Unit]                  = noop(request)
  def onChatBoost(boost: ChatBoostUpdated): F[Unit]                         = noop(boost)
  def onRemovedChatBoost(boostRemoved: ChatBoostRemoved): F[Unit]           = noop(boostRemoved)

  def onUpdate(update: Update): F[Unit] =
    for {
      _ <- update.message.fold(Monad[F].unit)(onMessage)
      _ <- update.editedMessage.fold(Monad[F].unit)(onEditedMessage)
      _ <- update.channelPost.fold(Monad[F].unit)(onChannelPost)
      _ <- update.editedChannelPost.fold(Monad[F].unit)(onEditedChannelPost)
      _ <- update.businessConnection.fold(Monad[F].unit)(onBusinessConnection)
      _ <- update.businessMessage.fold(Monad[F].unit)(onBusinessMessage)
      _ <- update.editedBusinessMessage.fold(Monad[F].unit)(onEditedBusinessMessage)
      _ <- update.deletedBusinessMessages.fold(Monad[F].unit)(onDeletedBusinessMessages)
      _ <- update.messageReaction.fold(Monad[F].unit)(onMessageReaction)
      _ <- update.messageReactionCount.fold(Monad[F].unit)(onMessageReactionCount)
      _ <- update.inlineQuery.fold(Monad[F].unit)(onInlineQuery)
      _ <- update.callbackQuery.fold(Monad[F].unit)(onCallbackQuery)
      _ <- update.chosenInlineResult.fold(Monad[F].unit)(onChosenInlineResult)
      _ <- update.shippingQuery.fold(Monad[F].unit)(onShippingQuery)
      _ <- update.preCheckoutQuery.fold(Monad[F].unit)(onPreCheckoutQuery)
      _ <- update.poll.fold(Monad[F].unit)(onPoll)
      _ <- update.pollAnswer.fold(Monad[F].unit)(onPollAnswer)
      _ <- update.myChatMember.fold(Monad[F].unit)(onMyChatMember)
      _ <- update.chatMember.fold(Monad[F].unit)(onChatMember)
      _ <- update.chatJoinRequest.fold(Monad[F].unit)(onChatJoinRequest)
      _ <- update.chatBoost.fold(Monad[F].unit)(onChatBoost)
      _ <- update.removedChatBoost.fold(Monad[F].unit)(onRemovedChatBoost)
    } yield ()

  def onError(e: Throwable): F[Unit] = {
    Async[F].delay(e.printStackTrace())
  }

  def poll(offsetKeeper: OffsetKeeper[F]): F[Unit] = {
    for {
      offset <- offsetKeeper.getOffset
      seconds = pollInterval.toSeconds.toInt
      updates <- bot
        .execute(getUpdates(offset = Some(offset), timeout = Some(seconds)))
        .onError {
          case _: java.util.concurrent.TimeoutException => poll(offsetKeeper)
          case NonFatal(e) =>
            for {
              _     <- onError(e)
              delay <- onErrorDelay
              _     <- Async[F].sleep(delay)
              _     <- poll(offsetKeeper)
            } yield ()
        }
      _ <- updates.parTraverse {
        onUpdate(_).recoverWith { case NonFatal(e) =>
          onError(e)
        }
      }
      _    <- updates.map(_.updateId).maximumOption.traverse(max => offsetKeeper.setOffset(max + 1))
      next <- poll(offsetKeeper)
    } yield {
      next
    }
  }

  def start(): F[Unit] = {
    for {
      _          <- bot.execute(deleteWebhook())
      refCounter <- Ref.of[F, Int](0)
      offsetKeeper = new OffsetKeeper[F] {
        def getOffset: F[Int]               = refCounter.get
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
    Async[F].delay(5.seconds)
  }

}

object LongPollBot {

  trait OffsetKeeper[F[_]] {
    def getOffset: F[Int]
    def setOffset(offset: Int): F[Unit]
  }

}
