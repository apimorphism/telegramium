package telegramium.bots.high

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.syntax.either._
import com.dimafeng.testcontainers.{ForAllTestContainer, MockServerContainer}
import io.circe.Json
import io.circe.parser.parse
import org.http4s.Request
import org.http4s.circe._
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.dsl.io._
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import org.mockserver.model.JsonBody
import org.scalatest.BeforeAndAfterAll
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class WebhookBotISpec extends AnyFreeSpec with ForAllTestContainer with BeforeAndAfterAll with Matchers {
  lazy val container: MockServerContainer = MockServerContainer("5.10.0")
  private val mockServer                  = container.container

  private val (httpClient, finalizer) = BlazeClientBuilder[IO](global.compute).resource.allocated.unsafeRunSync()
  private lazy val api                = BotApi(httpClient, mockServer.getEndpoint)
  private lazy val bot1               = new TestWebhookBot(api, "/bot1")
  private lazy val bot2               = new TestWebhookBot(api, "/bot2")

  "should set a webhook and accept requests" in {
    prepareHttpMocks()
    bot1
      .start("localhost", 0)(global.compute)
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

  override protected def afterAll(): Unit = {
    finalizer.unsafeRunSync()
  }

  private def prepareHttpMocks(): Unit = {
    val mockServerClient = new MockServerClient("localhost", mockServer.getServerPort)
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
  }

}
