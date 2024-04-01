package telegramium.bots.high

import cats.effect.IO

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

class TestLongPollBot(api: Api[IO]) extends LongPollBot[IO](api) {
  private def sendMessageTask(text: String) = api.execute(sendMessage(ChatIntId(0), text)).void

  override def onMessage(msg: Message): IO[Unit]                              = sendMessageTask("onMessage")
  override def onEditedMessage(msg: Message): IO[Unit]                        = sendMessageTask("onEditedMessage")
  override def onChannelPost(msg: Message): IO[Unit]                          = sendMessageTask("onChannelPost")
  override def onEditedChannelPost(msg: Message): IO[Unit]                    = sendMessageTask("onEditedChannelPost")
  override def onBusinessConnection(connection: BusinessConnection): IO[Unit] = sendMessageTask("onBusinessConnection")
  override def onBusinessMessage(msg: Message): IO[Unit]                      = sendMessageTask("onBusinessMessage")
  override def onEditedBusinessMessage(msg: Message): IO[Unit] = sendMessageTask("onEditedBusinessMessage")
  override def onDeletedBusinessMessages(messages: BusinessMessagesDeleted): IO[Unit] =
    sendMessageTask("onDeletedBusinessMessages")
  override def onMessageReaction(reaction: MessageReactionUpdated): IO[Unit] = sendMessageTask("onMessageReaction")
  override def onMessageReactionCount(count: MessageReactionCountUpdated): IO[Unit] = sendMessageTask(
    "onMessageReactionCount"
  )
  override def onInlineQuery(query: InlineQuery): IO[Unit]     = sendMessageTask("onInlineQuery")
  override def onCallbackQuery(query: CallbackQuery): IO[Unit] = sendMessageTask("onCallbackQuery")
  override def onChosenInlineResult(inlineResult: ChosenInlineResult): IO[Unit] = sendMessageTask(
    "onChosenInlineResult"
  )
  override def onShippingQuery(query: ShippingQuery): IO[Unit]              = sendMessageTask("onShippingQuery")
  override def onPreCheckoutQuery(query: PreCheckoutQuery): IO[Unit]        = sendMessageTask("onPreCheckoutQuery")
  override def onPoll(poll: Poll): IO[Unit]                                 = sendMessageTask("onPoll")
  override def onPollAnswer(pollAnswer: PollAnswer): IO[Unit]               = sendMessageTask("onPollAnswer")
  override def onMyChatMember(myChatMember: ChatMemberUpdated): IO[Unit]    = sendMessageTask("onMyChatMember")
  override def onChatMember(chatMember: ChatMemberUpdated): IO[Unit]        = sendMessageTask("onChatMember")
  override def onChatJoinRequest(request: ChatJoinRequest): IO[Unit]        = sendMessageTask("onChatJoinRequest")
  override def onChatBoost(boost: ChatBoostUpdated): IO[Unit]               = sendMessageTask("onChatBoost")
  override def onRemovedChatBoost(boostRemoved: ChatBoostRemoved): IO[Unit] = sendMessageTask("onRemovedChatBoost")
}
