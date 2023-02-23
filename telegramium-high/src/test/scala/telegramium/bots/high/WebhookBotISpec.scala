package telegramium.bots.high

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.syntax.either.*
import cats.syntax.option.*
import com.dimafeng.testcontainers.{ForAllTestContainer, MockServerContainer}
import io.circe.Json
import io.circe.parser.parse
import io.circe.syntax.*
import org.http4s.Request
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.circe.*
import org.http4s.dsl.io.*
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import org.mockserver.model.JsonBody
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterAll, OptionValues}
import telegramium.bots.CirceImplicits.*
import telegramium.bots.client.CirceImplicits.*
import telegramium.bots.client.{MethodReq, SendMessageReq}
import telegramium.bots.high.HttpMocks.*
import telegramium.bots.{CallbackQuery, Chat, ChatIntId, ChatJoinRequest, ChatMemberMember, ChatMemberUpdated, ChosenInlineResult, InlineQuery, Message, Poll, PollAnswer, PreCheckoutQuery, ShippingAddress, ShippingQuery, Update, User}

class WebhookBotISpec
    extends AnyFreeSpec
    with ForAllTestContainer
    with BeforeAndAfterAll
    with Matchers
    with OptionValues {
  lazy val container: MockServerContainer = MockServerContainer(MockServerContainerVersion)
  private val mockServer                  = container.container

  private val (httpClient, finalizer) = BlazeClientBuilder[IO].resource.allocated.unsafeRunSync()
  private lazy val mockServerClient   = new MockServerClient("localhost", mockServer.getServerPort)
  private lazy val api                = BotApi(httpClient, mockServer.getEndpoint)
  private lazy val bot1               = new TestWebhookBot(api, "/bot1")
  private lazy val bot2               = new TestWebhookBot(api, "/bot2")

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

  private val testChatJoinRequest =
    ChatJoinRequest(
      Chat(0, `type` = ""),
      testUser,
      0L,
      0
    )

  "should set a webhook and accept requests" in {
    prepareHttpMocks()
    bot1
      .start(0)
      .use { server =>
        val request = Request[IO]()
          .withMethod(POST)
          .withUri(server.baseUri / "bot1")
          .withEntity(
            parse(
              """
                {
                  "update_id": 0,
                  "message": {
                    "message_id": 0,
                    "date": 1593365356,
                    "chat": {
                      "id": 0,
                      "type": "private"
                    },
                    "text": "Lorem ipsum"
                  }
                }
              """
            ).valueOr(throw _)
          )
        httpClient.expect[Json](request).unsafeRunSync() shouldBe parse(
          """
            {
              "chat_id": 0,
              "text": "onMessageReply",
              "entities": [],
              "method": "sendMessage"
            }
          """
        ).valueOr(throw _)
        IO.unit
      }
      .unsafeRunSync()
  }

  "composing two webhook bot should result in a server handling requests for both bots" in {
    prepareHttpMocks()
    WebhookBot
      .compose(List(bot1, bot2), 0)
      .use { server =>
        val request1 = Request[IO]()
          .withMethod(POST)
          .withUri(server.baseUri / "bot1")
          .withEntity(
            parse(
              """
                {
                  "update_id": 0,
                  "message": {
                    "message_id": 0,
                    "date": 1593365356,
                    "chat": {
                      "id": 0,
                      "type": "private"
                    },
                    "text": "Lorem ipsum"
                  }
                }
              """
            ).valueOr(throw _)
          )
        httpClient.expect[Json](request1).unsafeRunSync() shouldBe parse(
          """
            {
              "chat_id": 0,
              "text": "onMessageReply",
              "entities": [],
              "method": "sendMessage"
            }
          """
        ).valueOr(throw _)
        val request2 = Request[IO]()
          .withMethod(POST)
          .withUri(server.baseUri / "bot2")
          .withEntity(
            parse(
              """
                {
                  "update_id": 0,
                  "message": {
                    "message_id": 0,
                    "date": 1593365356,
                    "chat": {
                      "id": 0,
                      "type": "private"
                    },
                    "text": "Lorem ipsum"
                  }
                }
              """
            ).valueOr(throw _)
          )
        httpClient.expect[Json](request2).unsafeRunSync() shouldBe parse(
          """
            {
              "chat_id": 0,
              "text": "onMessageReply",
              "entities": [],
              "method": "sendMessage"
            }
          """
        ).valueOr(throw _)
        IO.unit
      }
      .unsafeRunSync()
  }

  "should support all Update types" - {
    "message" in {
      mockServerClient
        .when(sendMessageRequest("onMessage"))
        .respond(sendMessageResponse)
      verifyResult(testUpdate.copy(message = testMessage.some), "onMessageReply")
    }

    "edited message" in {
      mockServerClient
        .when(sendMessageRequest("onEditedMessage"))
        .respond(sendMessageResponse)
      verifyResult(testUpdate.copy(editedMessage = testMessage.some), "onEditedMessageReply")
    }

    "channel post" in {
      mockServerClient
        .when(sendMessageRequest("onChannelPost"))
        .respond(sendMessageResponse)
      verifyResult(testUpdate.copy(channelPost = testMessage.some), "onChannelPostReply")
    }

    "edited channel post" in {
      mockServerClient
        .when(sendMessageRequest("onEditedChannelPost"))
        .respond(sendMessageResponse)
      verifyResult(testUpdate.copy(editedChannelPost = testMessage.some), "onEditedChannelPostReply")
    }

    "inline query" in {
      mockServerClient
        .when(sendMessageRequest("onInlineQuery"))
        .respond(sendMessageResponse)
      verifyResult(
        testUpdate.copy(inlineQuery = InlineQuery("0", testUser, query = "", offset = "0").some),
        "onInlineQueryReply"
      )
    }

    "chosen inline result" in {
      mockServerClient
        .when(sendMessageRequest("onChosenInlineResult"))
        .respond(sendMessageResponse)
      verifyResult(
        testUpdate.copy(chosenInlineResult = ChosenInlineResult("0", testUser, query = "").some),
        "onChosenInlineResultReply"
      )
    }

    "callback query" in {
      mockServerClient
        .when(sendMessageRequest("onCallbackQuery"))
        .respond(sendMessageResponse)
      verifyResult(
        testUpdate.copy(callbackQuery = CallbackQuery("0", testUser, chatInstance = "").some),
        "onCallbackQueryReply"
      )
    }

    "shipping query" in {
      mockServerClient
        .when(sendMessageRequest("onShippingQuery"))
        .respond(sendMessageResponse)
      verifyResult(
        testUpdate.copy(shippingQuery = ShippingQuery("0", testUser, "", ShippingAddress("", "", "", "", "", "")).some),
        "onShippingQueryReply"
      )
    }

    "pre-checkout query" in {
      mockServerClient
        .when(sendMessageRequest("onPreCheckoutQuery"))
        .respond(sendMessageResponse)
      verifyResult(
        testUpdate.copy(preCheckoutQuery = PreCheckoutQuery("0", testUser, "", 0, "").some),
        "onPreCheckoutQueryReply"
      )
    }

    "poll" in {
      mockServerClient
        .when(sendMessageRequest("onPoll"))
        .respond(sendMessageResponse)
      verifyResult(
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
        ),
        "onPollReply"
      )
    }

    "poll answer" in {
      mockServerClient
        .when(sendMessageRequest("onPollAnswer"))
        .respond(sendMessageResponse)
      verifyResult(testUpdate.copy(pollAnswer = PollAnswer("0", testUser).some), "onPollAnswerReply")
    }

    "The bot's chat member status was updated in a chat" in {
      mockServerClient
        .when(sendMessageRequest("onMyChatMember"))
        .respond(sendMessageResponse)
      verifyResult(testUpdate.copy(myChatMember = testChatMemberUpdated.some), "onMyChatMemberReply")
    }

    "A chat member's status was updated in a chat" in {
      mockServerClient
        .when(sendMessageRequest("onChatMember"))
        .respond(sendMessageResponse)
      verifyResult(testUpdate.copy(chatMember = testChatMemberUpdated.some), "onChatMemberReply")
    }

    "chat join request" in {
      mockServerClient
        .when(sendMessageRequest("onChatJoinRequest"))
        .respond(sendMessageResponse)
      verifyResult(testUpdate.copy(chatJoinRequest = testChatJoinRequest.some), "onChatJoinRequestReply")
    }
  }

  override protected def afterAll(): Unit = {
    finalizer.unsafeRunSync()
  }

  private def verifyResult(update: Update, expectedText: String) =
    bot1.onUpdate(update).unsafeRunSync().value shouldBe MethodReq[Message](
      "sendMessage",
      SendMessageReq(ChatIntId(0), expectedText).asJson
    )

  private def prepareHttpMocks(): Unit = {
    mockServerClient
      .when(
        request()
          .withMethod("POST")
          .withPath("/setWebhook")
          .withBody(
            new JsonBody(
              """
                {
                  "url": "localhost",
                  "method": "setWebhook"
                }
              """
            )
          )
      )
      .respond(
        response().withBody(
          new JsonBody(
            """
              {
                "ok": true,
                "result": true
              }
            """
          )
        )
      )
    mockServerClient
      .when(
        request()
          .withMethod("POST")
          .withPath("/deleteWebhook")
      )
      .respond(
        response().withBody(
          new JsonBody(
            """
              {
                "ok": true,
                "result": true
              }
            """
          )
        )
      )
    mockServerClient
      .when(
        request()
          .withPath("/sendMessage")
          .withMethod("POST")
          .withBody(
            new JsonBody(
              """
                {
                  "chat_id": 0,
                  "text": "onMessage",
                  "method": "sendMessage"
                }
              """
            )
          )
      )
      .respond(
        response().withBody(
          new JsonBody(
            """
              {
                "ok": true,
                "result": {
                  "message_id": 0,
                  "date": 1593365356,
                  "chat": {
                    "id": 0,
                    "type": "private"
                  },
                  "text": "onMessage"
                }
              }
            """
          )
        )
      )
    ()
  }

}
