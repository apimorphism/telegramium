package telegramium.bots.high

import cats.effect.IO
import cats.syntax.option.*

import telegramium.bots.BusinessConnection
import telegramium.bots.BusinessMessagesDeleted
import telegramium.bots.CallbackQuery
import telegramium.bots.ChatBoostRemoved
import telegramium.bots.ChatBoostUpdated
import telegramium.bots.ChatIntId
import telegramium.bots.ChatJoinRequest
import telegramium.bots.ChatMemberUpdated
import telegramium.bots.ChosenInlineResult
import telegramium.bots.InlineQuery
import telegramium.bots.Message
import telegramium.bots.MessageReactionCountUpdated
import telegramium.bots.MessageReactionUpdated
import telegramium.bots.Poll
import telegramium.bots.PollAnswer
import telegramium.bots.PreCheckoutQuery
import telegramium.bots.ShippingQuery
import telegramium.bots.client.Method

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

  override def onBusinessConnection(connection: BusinessConnection): IO[Unit] =
    api.execute(sendMessageMethod("onBusinessConnection")).void

  override def onBusinessMessage(msg: Message): IO[Unit] =
    api.execute(sendMessageMethod("onBusinessMessage")).void

  override def onEditedBusinessMessage(msg: Message): IO[Unit] =
    api.execute(sendMessageMethod("onEditedBusinessMessage")).void

  override def onDeletedBusinessMessages(messages: BusinessMessagesDeleted): IO[Unit] =
    api.execute(sendMessageMethod("onDeletedBusinessMessages")).void

  override def onMessageReaction(reaction: MessageReactionUpdated): IO[Unit] =
    api.execute(sendMessageMethod("onMessageReaction")).void

  override def onMessageReactionCount(count: MessageReactionCountUpdated): IO[Unit] =
    api.execute(sendMessageMethod("onMessageReactionCount")).void

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

  override def onChatJoinRequest(request: ChatJoinRequest): IO[Unit] =
    api.execute(sendMessageMethod("onChatJoinRequest")).void

  override def onChatBoost(boost: ChatBoostUpdated): IO[Unit] =
    api.execute(sendMessageMethod("onChatBoost")).void

  override def onRemovedChatBoost(boostRemoved: ChatBoostRemoved): IO[Unit] =
    api.execute(sendMessageMethod("onRemovedChatBoost")).void

  override def onMessageReply(msg: Message): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onMessageReply").some)

  override def onEditedMessageReply(msg: Message): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onEditedMessageReply").some)

  override def onChannelPostReply(msg: Message): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onChannelPostReply").some)

  override def onEditedChannelPostReply(msg: Message): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onEditedChannelPostReply").some)

  override def onBusinessConnectionReply(connection: BusinessConnection): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onBusinessConnectionReply").some)

  override def onBusinessMessageReply(msg: Message): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onBusinessMessageReply").some)

  override def onEditedBusinessMessageReply(msg: Message): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onEditedBusinessMessageReply").some)

  override def onDeletedBusinessMessagesReply(messages: BusinessMessagesDeleted): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onDeletedBusinessMessagesReply").some)

  override def onMessageReactionReply(reaction: MessageReactionUpdated): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onMessageReactionReply").some)

  override def onMessageReactionCountReply(count: MessageReactionCountUpdated): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onMessageReactionCountReply").some)

  override def onInlineQueryReply(query: InlineQuery): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onInlineQueryReply").some)

  override def onCallbackQueryReply(query: CallbackQuery): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onCallbackQueryReply").some)

  override def onChosenInlineResultReply(inlineResult: ChosenInlineResult): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onChosenInlineResultReply").some)

  override def onShippingQueryReply(query: ShippingQuery): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onShippingQueryReply").some)

  override def onPreCheckoutQueryReply(query: PreCheckoutQuery): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onPreCheckoutQueryReply").some)

  override def onPollReply(poll: Poll): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onPollReply").some)

  override def onPollAnswerReply(pollAnswer: PollAnswer): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onPollAnswerReply").some)

  override def onMyChatMemberReply(myChatMember: ChatMemberUpdated): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onMyChatMemberReply").some)

  override def onChatMemberReply(chatMember: ChatMemberUpdated): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onChatMemberReply").some)

  override def onChatJoinRequestReply(request: ChatJoinRequest): IO[Option[Method[?]]] =
    IO.pure(sendMessageMethod("onChatJoinRequestReply").some)

  override def onChatBoostReply(boost: ChatBoostUpdated): IO[Option[Method[_]]] =
    IO.pure(sendMessageMethod("onChatBoostReply").some)

  override def onRemovedChatBoostReply(boostRemoved: ChatBoostRemoved): IO[Option[Method[_]]] =
    IO.pure(sendMessageMethod("onRemovedChatBoostReply").some)

}
