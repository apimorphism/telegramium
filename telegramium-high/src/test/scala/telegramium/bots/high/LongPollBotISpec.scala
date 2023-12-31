package telegramium.bots.high

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.syntax.option.*

import com.dimafeng.testcontainers.ForAllTestContainer
import com.dimafeng.testcontainers.MockServerContainer
import org.http4s.blaze.client.BlazeClientBuilder
import org.mockserver.client.MockServerClient
import org.scalatest.BeforeAndAfterAll
import org.scalatest.OptionValues
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import telegramium.bots.CallbackQuery
import telegramium.bots.Chat
import telegramium.bots.ChatBoost
import telegramium.bots.ChatBoostRemoved
import telegramium.bots.ChatBoostSourcePremium
import telegramium.bots.ChatBoostUpdated
import telegramium.bots.ChatJoinRequest
import telegramium.bots.ChatMemberMember
import telegramium.bots.ChatMemberUpdated
import telegramium.bots.ChosenInlineResult
import telegramium.bots.InlineQuery
import telegramium.bots.Message
import telegramium.bots.MessageReactionCountUpdated
import telegramium.bots.MessageReactionUpdated
import telegramium.bots.Poll
import telegramium.bots.PollAnswer
import telegramium.bots.PreCheckoutQuery
import telegramium.bots.ShippingAddress
import telegramium.bots.ShippingQuery
import telegramium.bots.Update
import telegramium.bots.User
import telegramium.bots.high.HttpMocks.sendMessageRequest
import telegramium.bots.high.HttpMocks.sendMessageResponse

class LongPollBotISpec
    extends AnyFreeSpec
    with Matchers
    with OptionValues
    with ForAllTestContainer
    with BeforeAndAfterAll {
  lazy val container: MockServerContainer = MockServerContainer(MockServerContainerVersion)
  private val mockServer                  = container.container

  private lazy val mockServerClient   = new MockServerClient("localhost", mockServer.getServerPort)
  private val (httpClient, finalizer) = BlazeClientBuilder[IO].resource.allocated.unsafeRunSync()
  private lazy val api                = BotApi(httpClient, mockServer.getEndpoint)
  private lazy val bot                = new TestLongPollBot(api)

  private val testUpdate  = Update(updateId = 0)
  private val testChat    = Chat(0, `type` = "")
  private val testMessage = Message(0, date = 0, chat = testChat)
  private val testUser    = User(0, isBot = false, "")

  private val testChatMemberUpdated =
    ChatMemberUpdated(
      testChat,
      testUser,
      0,
      ChatMemberMember(testUser),
      ChatMemberMember(testUser)
    )

  private val testChatJoinRequest =
    ChatJoinRequest(
      testChat,
      testUser,
      0L,
      0
    )

  "should support all Update types" - {
    "message" in {
      mockServerClient
        .when(sendMessageRequest("onMessage"))
        .respond(sendMessageResponse)
      bot.onUpdate(testUpdate.copy(message = testMessage.some)).unsafeRunSync()
      verifyMessageSent("onMessage")
    }

    "edited message" in {
      mockServerClient
        .when(sendMessageRequest("onEditedMessage"))
        .respond(sendMessageResponse)
      bot.onUpdate(testUpdate.copy(editedMessage = testMessage.some)).unsafeRunSync()
      verifyMessageSent("onEditedMessage")
    }

    "channel post" in {
      mockServerClient
        .when(sendMessageRequest("onChannelPost"))
        .respond(sendMessageResponse)
      bot.onUpdate(testUpdate.copy(channelPost = testMessage.some)).unsafeRunSync()
      verifyMessageSent("onChannelPost")
    }

    "edited channel post" in {
      mockServerClient
        .when(sendMessageRequest("onEditedChannelPost"))
        .respond(sendMessageResponse)
      bot.onUpdate(testUpdate.copy(editedChannelPost = testMessage.some)).unsafeRunSync()
      verifyMessageSent("onEditedChannelPost")
    }

    "message reaction" in {
      mockServerClient
        .when(sendMessageRequest("onMessageReaction"))
        .respond(sendMessageResponse)
      bot
        .onUpdate(
          testUpdate.copy(messageReaction = MessageReactionUpdated(testChat, 0, 0).some)
        )
        .unsafeRunSync()
      verifyMessageSent("onMessageReaction")
    }

    "message reaction count" in {
      mockServerClient
        .when(sendMessageRequest("onMessageReactionCount"))
        .respond(sendMessageResponse)
      bot
        .onUpdate(
          testUpdate.copy(messageReactionCount = MessageReactionCountUpdated(testChat, 0, 0).some)
        )
        .unsafeRunSync()
      verifyMessageSent("onMessageReactionCount")
    }

    "inline query" in {
      mockServerClient
        .when(sendMessageRequest("onInlineQuery"))
        .respond(sendMessageResponse)
      bot
        .onUpdate(testUpdate.copy(inlineQuery = InlineQuery("0", testUser, query = "", offset = "0").some))
        .unsafeRunSync()
      verifyMessageSent("onInlineQuery")
    }

    "chosen inline result" in {
      mockServerClient
        .when(sendMessageRequest("onChosenInlineResult"))
        .respond(sendMessageResponse)
      bot
        .onUpdate(testUpdate.copy(chosenInlineResult = ChosenInlineResult("0", testUser, query = "").some))
        .unsafeRunSync()
      verifyMessageSent("onChosenInlineResult")
    }

    "callback query" in {
      mockServerClient
        .when(sendMessageRequest("onCallbackQuery"))
        .respond(sendMessageResponse)
      bot
        .onUpdate(testUpdate.copy(callbackQuery = CallbackQuery("0", testUser, chatInstance = "").some))
        .unsafeRunSync()
      verifyMessageSent("onCallbackQuery")
    }

    "shipping query" in {
      mockServerClient
        .when(sendMessageRequest("onShippingQuery"))
        .respond(sendMessageResponse)
      bot
        .onUpdate(
          testUpdate
            .copy(shippingQuery = ShippingQuery("0", testUser, "", ShippingAddress("", "", "", "", "", "")).some)
        )
        .unsafeRunSync()
      verifyMessageSent("onShippingQuery")
    }

    "pre-checkout query" in {
      mockServerClient
        .when(sendMessageRequest("onPreCheckoutQuery"))
        .respond(sendMessageResponse)
      bot.onUpdate(testUpdate.copy(preCheckoutQuery = PreCheckoutQuery("0", testUser, "", 0, "").some)).unsafeRunSync()
      verifyMessageSent("onPreCheckoutQuery")
    }

    "poll" in {
      mockServerClient
        .when(sendMessageRequest("onPoll"))
        .respond(sendMessageResponse)
      bot
        .onUpdate(
          testUpdate.copy(poll =
            Poll(
              "0",
              "",
              totalVoterCount = 0,
              isClosed = false,
              isAnonymous = false,
              `type` = "",
              allowsMultipleAnswers = false
            ).some
          )
        )
        .unsafeRunSync()
      verifyMessageSent("onPoll")
    }

    "poll answer" in {
      mockServerClient
        .when(sendMessageRequest("onPollAnswer"))
        .respond(sendMessageResponse)
      bot.onUpdate(testUpdate.copy(pollAnswer = PollAnswer("0", user = testUser.some).some)).unsafeRunSync()
      verifyMessageSent("onPollAnswer")
    }

    "The bot's chat member status was updated in a chat" in {
      mockServerClient
        .when(sendMessageRequest("onMyChatMember"))
        .respond(sendMessageResponse)
      bot.onUpdate(testUpdate.copy(myChatMember = testChatMemberUpdated.some)).unsafeRunSync()
      verifyMessageSent("onMyChatMember")
    }

    "A chat member's status was updated in a chat" in {
      mockServerClient
        .when(sendMessageRequest("onChatMember"))
        .respond(sendMessageResponse)
      bot.onUpdate(testUpdate.copy(chatMember = testChatMemberUpdated.some)).unsafeRunSync()
      verifyMessageSent("onChatMember")
    }

    "chat join request" in {
      mockServerClient
        .when(sendMessageRequest("onChatJoinRequest"))
        .respond(sendMessageResponse)
      bot.onUpdate(testUpdate.copy(chatJoinRequest = testChatJoinRequest.some)).unsafeRunSync()
      verifyMessageSent("onChatJoinRequest")
    }

    "chat boost" in {
      mockServerClient
        .when(sendMessageRequest("onChatBoost"))
        .respond(sendMessageResponse)
      bot
        .onUpdate(
          testUpdate
            .copy(chatBoost = ChatBoostUpdated(testChat, ChatBoost("", 0, 0, ChatBoostSourcePremium(testUser))).some)
        )
        .unsafeRunSync()
      mockServerClient.verify(sendMessageRequest("onChatBoost"))
    }

    "removed chat boost" in {
      mockServerClient
        .when(sendMessageRequest("onRemovedChatBoost"))
        .respond(sendMessageResponse)
      bot
        .onUpdate(
          testUpdate
            .copy(removedChatBoost = ChatBoostRemoved(testChat, "", 0, ChatBoostSourcePremium(testUser)).some)
        )
        .unsafeRunSync()
      verifyMessageSent("onRemovedChatBoost")
    }
  }

  private def verifyMessageSent(text: String): Unit = {
    mockServerClient.verify(sendMessageRequest(text))
  }

  override protected def afterAll(): Unit = {
    finalizer.unsafeRunSync()
  }

}
