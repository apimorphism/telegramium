package telegramium.bots.high

import cats.effect.Blocker
import cats.syntax.option._
import monix.eval.Task
import monix.execution.Scheduler
import monix.execution.Scheduler.Implicits.global
import telegramium.bots.client.Method
import telegramium.bots.{
  CallbackQuery,
  ChatIntId,
  ChatMemberUpdated,
  ChosenInlineResult,
  InlineQuery,
  Message,
  Poll,
  PollAnswer,
  PreCheckoutQuery,
  ShippingQuery
}

class TestWebhookBot(api: Api[Task])
    extends WebhookBot[Task](api, 0, "localhost", blocker = Blocker.liftExecutionContext(Scheduler.io())) {
  private def sendMessageMethod(text: String) = sendMessage(ChatIntId(0), text)

  override def onMessage(msg: Message): Task[Unit] =
    api.execute(sendMessageMethod("onMessage")).void
  override def onEditedMessage(msg: Message): Task[Unit] =
    api.execute(sendMessageMethod("onEditedMessage")).void
  override def onChannelPost(msg: Message): Task[Unit] =
    api.execute(sendMessageMethod("onChannelPost")).void
  override def onEditedChannelPost(msg: Message): Task[Unit] =
    api.execute(sendMessageMethod("onEditedChannelPost")).void
  override def onInlineQuery(query: InlineQuery): Task[Unit] =
    api.execute(sendMessageMethod("onInlineQuery")).void
  override def onCallbackQuery(query: CallbackQuery): Task[Unit] =
    api.execute(sendMessageMethod("onCallbackQuery")).void
  override def onChosenInlineResult(inlineResult: ChosenInlineResult): Task[Unit] =
    api.execute(sendMessageMethod("onChosenInlineResult")).void
  override def onShippingQuery(query: ShippingQuery): Task[Unit] =
    api.execute(sendMessageMethod("onShippingQuery")).void
  override def onPreCheckoutQuery(query: PreCheckoutQuery): Task[Unit] =
    api.execute(sendMessageMethod("onPreCheckoutQuery")).void
  override def onPoll(poll: Poll): Task[Unit] =
    api.execute(sendMessageMethod("onPoll")).void
  override def onPollAnswer(pollAnswer: PollAnswer): Task[Unit] =
    api.execute(sendMessageMethod("onPollAnswer")).void
  override def onMyChatMember(myChatMember: ChatMemberUpdated): Task[Unit] =
    api.execute(sendMessageMethod("onMyChatMember")).void
  override def onChatMember(chatMember: ChatMemberUpdated): Task[Unit] =
    api.execute(sendMessageMethod("onChatMember")).void

  override def onMessageReply(msg: Message): Task[Option[Method[_]]] =
    Task.pure(sendMessageMethod("onMessageReply").some)
  override def onEditedMessageReply(msg: Message): Task[Option[Method[_]]] =
    Task.pure(sendMessageMethod("onEditedMessageReply").some)
  override def onChannelPostReply(msg: Message): Task[Option[Method[_]]] =
    Task.pure(sendMessageMethod("onChannelPostReply").some)
  override def onEditedChannelPostReply(msg: Message): Task[Option[Method[_]]] =
    Task.pure(sendMessageMethod("onEditedChannelPostReply").some)
  override def onInlineQueryReply(query: InlineQuery): Task[Option[Method[_]]] =
    Task.pure(sendMessageMethod("onInlineQueryReply").some)
  override def onCallbackQueryReply(query: CallbackQuery): Task[Option[Method[_]]] =
    Task.pure(sendMessageMethod("onCallbackQueryReply").some)
  override def onChosenInlineResultReply(inlineResult: ChosenInlineResult): Task[Option[Method[_]]] =
    Task.pure(sendMessageMethod("onChosenInlineResultReply").some)
  override def onShippingQueryReply(query: ShippingQuery): Task[Option[Method[_]]] =
    Task.pure(sendMessageMethod("onShippingQueryReply").some)
  override def onPreCheckoutQueryReply(query: PreCheckoutQuery): Task[Option[Method[_]]] =
    Task.pure(sendMessageMethod("onPreCheckoutQueryReply").some)
  override def onPollReply(poll: Poll): Task[Option[Method[_]]] =
    Task.pure(sendMessageMethod("onPollReply").some)
  override def onPollAnswerReply(pollAnswer: PollAnswer): Task[Option[Method[_]]] =
    Task.pure(sendMessageMethod("onPollAnswerReply").some)
  override def onMyChatMemberReply(myChatMember: ChatMemberUpdated): Task[Option[Method[_]]] =
    Task.pure(sendMessageMethod("onMyChatMemberReply").some)
  override def onChatMemberReply(chatMember: ChatMemberUpdated): Task[Option[Method[_]]] =
    Task.pure(sendMessageMethod("onChatMemberReply").some)
}
