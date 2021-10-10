package telegramium.bots.high

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.syntax.option._
import com.dimafeng.testcontainers.{ForAllTestContainer, MockServerContainer}
import org.http4s.blaze.client.BlazeClientBuilder
import org.mockserver.client.MockServerClient
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterAll, OptionValues}
import telegramium.bots.high.HttpMocks._
import telegramium.bots.{CallbackQuery, Chat, ChatMemberMember, ChatMemberUpdated, ChosenInlineResult, InlineQuery, Message, Poll, PollAnswer, PreCheckoutQuery, ShippingAddress, ShippingQuery, Update, User}

class LongPollBotISpec
    extends AnyFreeSpec
    with Matchers
    with OptionValues
    with ForAllTestContainer
    with BeforeAndAfterAll {
  lazy val container: MockServerContainer = MockServerContainer("5.11.2")
  private val mockServer                  = container.container

  private lazy val mockServerClient   = new MockServerClient("localhost", mockServer.getServerPort)
  private val (httpClient, finalizer) = BlazeClientBuilder[IO].resource.allocated.unsafeRunSync()
  private lazy val api                = BotApi(httpClient, mockServer.getEndpoint)
  private lazy val bot                = new TestLongPollBot(api)

  private val testUpdate  = Update(updateId = 0)
  private val testMessage = Message(0, date = 0, chat = Chat(0, `type` = ""))
  private val testUser    = User(0, isBot = false, "")

  private val testChatMemberUpdated =
    ChatMemberUpdated(
      Chat(0, `type` = ""),
      testUser,
      0,
      ChatMemberMember("", testUser),
      ChatMemberMember("", testUser)
    )

  "should support all Update types" - {
    "message" in {
      mockServerClient
        .when(sendMessageRequest("onMessage"))
        .respond(sendMessageResponse)
      bot.onUpdate(testUpdate.copy(message = testMessage.some)).unsafeRunSync()
    }

    "edited message" in {
      mockServerClient
        .when(sendMessageRequest("onEditedMessage"))
        .respond(sendMessageResponse)
      bot.onUpdate(testUpdate.copy(editedMessage = testMessage.some)).unsafeRunSync()
    }

    "channel post" in {
      mockServerClient
        .when(sendMessageRequest("onChannelPost"))
        .respond(sendMessageResponse)
      bot.onUpdate(testUpdate.copy(channelPost = testMessage.some)).unsafeRunSync()
    }

    "edited channel post" in {
      mockServerClient
        .when(sendMessageRequest("onEditedChannelPost"))
        .respond(sendMessageResponse)
      bot.onUpdate(testUpdate.copy(editedChannelPost = testMessage.some)).unsafeRunSync()
    }

    "inline query" in {
      mockServerClient
        .when(sendMessageRequest("onInlineQuery"))
        .respond(sendMessageResponse)
      bot
        .onUpdate(testUpdate.copy(inlineQuery = InlineQuery("0", testUser, query = "", offset = "0").some))
        .unsafeRunSync()
    }

    "chosen inline result" in {
      mockServerClient
        .when(sendMessageRequest("onChosenInlineResult"))
        .respond(sendMessageResponse)
      bot
        .onUpdate(testUpdate.copy(chosenInlineResult = ChosenInlineResult("0", testUser, query = "").some))
        .unsafeRunSync()
    }

    "callback query" in {
      mockServerClient
        .when(sendMessageRequest("onCallbackQuery"))
        .respond(sendMessageResponse)
      bot
        .onUpdate(testUpdate.copy(callbackQuery = CallbackQuery("0", testUser, chatInstance = "").some))
        .unsafeRunSync()
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
    }

    "pre-checkout query" in {
      mockServerClient
        .when(sendMessageRequest("onPreCheckoutQuery"))
        .respond(sendMessageResponse)
      bot.onUpdate(testUpdate.copy(preCheckoutQuery = PreCheckoutQuery("0", testUser, "", 0, "").some)).unsafeRunSync()
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
    }

    "poll answer" in {
      mockServerClient
        .when(sendMessageRequest("onPollAnswer"))
        .respond(sendMessageResponse)
      bot.onUpdate(testUpdate.copy(pollAnswer = PollAnswer("0", testUser).some)).unsafeRunSync()
    }

    "The bot's chat member status was updated in a chat" in {
      mockServerClient
        .when(sendMessageRequest("onMyChatMember"))
        .respond(sendMessageResponse)
      bot.onUpdate(testUpdate.copy(myChatMember = testChatMemberUpdated.some)).unsafeRunSync()
    }

    "A chat member's status was updated in a chat" in {
      mockServerClient
        .when(sendMessageRequest("onChatMember"))
        .respond(sendMessageResponse)
      bot.onUpdate(testUpdate.copy(chatMember = testChatMemberUpdated.some)).unsafeRunSync()
    }
  }

  override protected def afterAll(): Unit = {
    finalizer.unsafeRunSync()
  }

}
