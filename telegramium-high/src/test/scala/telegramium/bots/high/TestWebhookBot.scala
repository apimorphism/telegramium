package telegramium.bots.high

import cats.effect.IO
import cats.syntax.option.*
import telegramium.bots.client.Method
import telegramium.bots.{CallbackQuery, ChatIntId, ChatMemberUpdated, ChosenInlineResult, InlineQuery, Message, Poll, PollAnswer, PreCheckoutQuery, ShippingQuery}

class TestWebhookBot(api: Api[IO], path: String = "/")
    extends WebhookBot[IO](
      bot = api,
      url = "localhost",
      path = path
    ) {
  private def sendMessageMethod(text: String) = sendMessage(ChatIntId(0), text)

  override def onMessage(msg: Message): IO[Unit] =
    api.execute(sendMessageMethod("onMessage")).void

  override def onEditedMessage(msg: Message): IO[Unit] =
    api.execute(sendMessageMethod("onEditedMessage")).void

  override def onChannelPost(msg: Message): IO[Unit] =
    api.execute(sendMessageMethod("onChannelPost")).void

  override def onEditedChannelPost(msg: Message): IO[Unit] =
    api.execute(sendMessageMethod("onEditedChannelPost")).void

  override def onInlineQuery(query: InlineQuery): IO[Unit] =
    api.execute(sendMessageMethod("onInlineQuery")).void

  override def onCallbackQuery(query: CallbackQuery): IO[Unit] =
    api.execute(sendMessageMethod("onCallbackQuery")).void

  override def onChosenInlineResult(inlineResult: ChosenInlineResult): IO[Unit] =
    api.execute(sendMessageMethod("onChosenInlineResult")).void

  override def onShippingQuery(query: ShippingQuery): IO[Unit] =
    api.execute(sendMessageMethod("onShippingQuery")).void

  override def onPreCheckoutQuery(query: PreCheckoutQuery): IO[Unit] =
    api.execute(sendMessageMethod("onPreCheckoutQuery")).void

  override def onPoll(poll: Poll): IO[Unit] =
    api.execute(sendMessageMethod("onPoll")).void

  override def onPollAnswer(pollAnswer: PollAnswer): IO[Unit] =
    api.execute(sendMessageMethod("onPollAnswer")).void

  override def onMyChatMember(myChatMember: ChatMemberUpdated): IO[Unit] =
    api.execute(sendMessageMethod("onMyChatMember")).void

  override def onChatMember(chatMember: ChatMemberUpdated): IO[Unit] =
    api.execute(sendMessageMethod("onChatMember")).void

  override def onMessageReply(msg: Message): IO[Option[Method[_]]] =
    IO.pure(sendMessageMethod("onMessageReply").some)

  override def onEditedMessageReply(msg: Message): IO[Option[Method[_]]] =
    IO.pure(sendMessageMethod("onEditedMessageReply").some)

  override def onChannelPostReply(msg: Message): IO[Option[Method[_]]] =
    IO.pure(sendMessageMethod("onChannelPostReply").some)

  override def onEditedChannelPostReply(msg: Message): IO[Option[Method[_]]] =
    IO.pure(sendMessageMethod("onEditedChannelPostReply").some)

  override def onInlineQueryReply(query: InlineQuery): IO[Option[Method[_]]] =
    IO.pure(sendMessageMethod("onInlineQueryReply").some)

  override def onCallbackQueryReply(query: CallbackQuery): IO[Option[Method[_]]] =
    IO.pure(sendMessageMethod("onCallbackQueryReply").some)

  override def onChosenInlineResultReply(inlineResult: ChosenInlineResult): IO[Option[Method[_]]] =
    IO.pure(sendMessageMethod("onChosenInlineResultReply").some)

  override def onShippingQueryReply(query: ShippingQuery): IO[Option[Method[_]]] =
    IO.pure(sendMessageMethod("onShippingQueryReply").some)

  override def onPreCheckoutQueryReply(query: PreCheckoutQuery): IO[Option[Method[_]]] =
    IO.pure(sendMessageMethod("onPreCheckoutQueryReply").some)

  override def onPollReply(poll: Poll): IO[Option[Method[_]]] =
    IO.pure(sendMessageMethod("onPollReply").some)

  override def onPollAnswerReply(pollAnswer: PollAnswer): IO[Option[Method[_]]] =
    IO.pure(sendMessageMethod("onPollAnswerReply").some)

  override def onMyChatMemberReply(myChatMember: ChatMemberUpdated): IO[Option[Method[_]]] =
    IO.pure(sendMessageMethod("onMyChatMemberReply").some)

  override def onChatMemberReply(chatMember: ChatMemberUpdated): IO[Option[Method[_]]] =
    IO.pure(sendMessageMethod("onChatMemberReply").some)

}
