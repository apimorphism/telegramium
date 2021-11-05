package telegramium.bots.high

import cats.effect.IO
import telegramium.bots.{CallbackQuery, ChatIntId, ChatJoinRequest, ChatMemberUpdated, ChosenInlineResult, InlineQuery, Message, Poll, PollAnswer, PreCheckoutQuery, ShippingQuery}

class TestLongPollBot(api: Api[IO]) extends LongPollBot[IO](api) {
  private def sendMessageTask(text: String) = api.execute(sendMessage(ChatIntId(0), text)).void

  override def onMessage(msg: Message): IO[Unit]               = sendMessageTask("onMessage")
  override def onEditedMessage(msg: Message): IO[Unit]         = sendMessageTask("onEditedMessage")
  override def onChannelPost(msg: Message): IO[Unit]           = sendMessageTask("onChannelPost")
  override def onEditedChannelPost(msg: Message): IO[Unit]     = sendMessageTask("onEditedChannelPost")
  override def onInlineQuery(query: InlineQuery): IO[Unit]     = sendMessageTask("onInlineQuery")
  override def onCallbackQuery(query: CallbackQuery): IO[Unit] = sendMessageTask("onCallbackQuery")

  override def onChosenInlineResult(inlineResult: ChosenInlineResult): IO[Unit] = sendMessageTask(
    "onChosenInlineResult"
  )

  override def onShippingQuery(query: ShippingQuery): IO[Unit]           = sendMessageTask("onShippingQuery")
  override def onPreCheckoutQuery(query: PreCheckoutQuery): IO[Unit]     = sendMessageTask("onPreCheckoutQuery")
  override def onPoll(poll: Poll): IO[Unit]                              = sendMessageTask("onPoll")
  override def onPollAnswer(pollAnswer: PollAnswer): IO[Unit]            = sendMessageTask("onPollAnswer")
  override def onMyChatMember(myChatMember: ChatMemberUpdated): IO[Unit] = sendMessageTask("onMyChatMember")
  override def onChatMember(chatMember: ChatMemberUpdated): IO[Unit]     = sendMessageTask("onChatMember")
  override def onChatJoinRequest(request: ChatJoinRequest): IO[Unit]     = sendMessageTask("onChatJoinRequest")
}
