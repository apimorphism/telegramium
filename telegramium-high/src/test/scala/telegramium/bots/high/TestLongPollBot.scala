package telegramium.bots.high

import monix.eval.Task
import telegramium.bots.{CallbackQuery, ChatIntId, ChatMemberUpdated, ChosenInlineResult, InlineQuery, Message, Poll, PollAnswer, PreCheckoutQuery, ShippingQuery}

class TestLongPollBot(api: Api[Task]) extends LongPollBot[Task](api) {
  private def sendMessageTask(text: String) = api.execute(sendMessage(ChatIntId(0), text)).void

  override def onMessage(msg: Message): Task[Unit] = sendMessageTask("onMessage")
  override def onEditedMessage(msg: Message): Task[Unit] = sendMessageTask("onEditedMessage")
  override def onChannelPost(msg: Message): Task[Unit] = sendMessageTask("onChannelPost")
  override def onEditedChannelPost(msg: Message): Task[Unit] = sendMessageTask("onEditedChannelPost")
  override def onInlineQuery(query: InlineQuery): Task[Unit] = sendMessageTask("onInlineQuery")
  override def onCallbackQuery(query: CallbackQuery): Task[Unit] = sendMessageTask("onCallbackQuery")
  override def onChosenInlineResult(inlineResult: ChosenInlineResult): Task[Unit] = sendMessageTask("onChosenInlineResult")
  override def onShippingQuery(query: ShippingQuery): Task[Unit] = sendMessageTask("onShippingQuery")
  override def onPreCheckoutQuery(query: PreCheckoutQuery): Task[Unit] = sendMessageTask("onPreCheckoutQuery")
  override def onPoll(poll: Poll): Task[Unit] = sendMessageTask("onPoll")
  override def onPollAnswer(pollAnswer: PollAnswer): Task[Unit] = sendMessageTask("onPollAnswer")
  override def onMyChatMember(myChatMember: ChatMemberUpdated): Task[Unit] = sendMessageTask("onMyChatMember")
  override def onChatMember(chatMember: ChatMemberUpdated): Task[Unit] = sendMessageTask("onChatMember")
}
