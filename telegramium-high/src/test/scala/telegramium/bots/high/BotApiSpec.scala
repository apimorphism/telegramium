package telegramium.bots.high

import java.io.File

import com.dimafeng.testcontainers.{ForAllTestContainer, MockServerContainer}
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import org.http4s.client.blaze.BlazeClientBuilder
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import org.mockserver.model.JsonBody
import org.scalatest.BeforeAndAfterAll
import org.scalatest.freespec.AnyFreeSpec
import telegramium.bots.{ChatIntId, InputLinkFile, InputPartFile}

class BotApiSpec extends AnyFreeSpec with ForAllTestContainer with BeforeAndAfterAll {
  lazy val container: MockServerContainer = MockServerContainer("5.11.1")
  private val mockServer = container.container

  private val (httpClient, finalizer) = BlazeClientBuilder[Task](global).resource.allocated.runSyncUnsafe()
  private lazy val api = BotApi(httpClient, mockServer.getEndpoint)

  private val messageResult = new JsonBody(
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
          "text": "Lorem ipsum"
        }
      }
    """
  )

  "should make a request to the Telegram Bot API" in {
    new MockServerClient("localhost", mockServer.getServerPort)
      .when(
        request()
          .withPath("/sendMessage")
          .withMethod("POST")
          .withBody(new JsonBody(
            """
              {
                "chat_id": 0,
                "text": "Lorem ipsum",
                "method": "sendMessage"
              }
            """
          ))
      )
      .respond(response().withBody(messageResult))

    api.execute(Methods.sendMessage(ChatIntId(0L), "Lorem ipsum")).runSyncUnsafe()
  }

  "should send a file as multipart/form-data" in {
    new MockServerClient("localhost", mockServer.getServerPort)
      .when(
        request()
          .withPath("/sendDocument")
          .withMethod("POST")
          .withHeader("Content-Type", "multipart/form-data.*")
      )
      .respond(response().withBody(messageResult))
    val file = new File(this.getClass.getResource("/document.txt").getFile)
    api.execute(Methods.sendDocument(ChatIntId(0), InputPartFile(file))).runSyncUnsafe()
  }

  "should send a file by link as JSON" in {
    new MockServerClient("localhost", mockServer.getServerPort)
      .when(
        request()
          .withPath("/sendDocument")
          .withMethod("POST")
          .withBody(new JsonBody(
            """
              {
                "chat_id": 0,
                "document": "https://example.com/flowers.png"
              }
            """
          ))
      )
      .respond(response().withBody(messageResult))

    api.execute(Methods.sendDocument(ChatIntId(0), InputLinkFile("https://example.com/flowers.png"))).runSyncUnsafe()
  }

  override protected def afterAll(): Unit = {
    finalizer.runSyncUnsafe()
  }
}
