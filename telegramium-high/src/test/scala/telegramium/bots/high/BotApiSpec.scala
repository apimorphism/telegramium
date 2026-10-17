package telegramium.bots.high

import java.io.File

import cats.effect.IO
import cats.effect.unsafe.implicits.global

import com.dimafeng.testcontainers.ForAllTestContainer
import com.dimafeng.testcontainers.MockServerContainer
import fs2.Stream
import org.http4s.blaze.client.BlazeClientBuilder
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import org.mockserver.model.JsonBody
import org.scalatest.BeforeAndAfterAll
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import telegramium.bots.ChatIntId
import telegramium.bots.InputLinkFile
import telegramium.bots.InputMediaDocument
import telegramium.bots.InputPartFile
import telegramium.bots.Message

class BotApiSpec extends AnyFreeSpec with ForAllTestContainer with BeforeAndAfterAll with Matchers {
  lazy val container: MockServerContainer = MockServerContainer(MockServerContainerVersion)
  private val mockServer                  = container.container

  private val (httpClient, finalizer) = BlazeClientBuilder[IO].resource.allocated.unsafeRunSync()
  private lazy val api                = BotApi(httpClient, mockServer.getEndpoint)

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

  private val errorResult = new JsonBody(
    """
      {
        "ok": false,
        "description": "Telegram Bot API error",
        "error_code": 400
      }
    """
  )

  "should make a request to the Telegram Bot API" in {
    new MockServerClient("localhost", mockServer.getServerPort)
      .when(
        request()
          .withPath("/sendMessage")
          .withMethod("POST")
          .withBody(
            new JsonBody(
              """
                {
                  "chat_id": 0,
                  "text": "Lorem ipsum",
                  "method": "sendMessage"
                }
              """
            )
          )
      )
      .respond(response().withBody(messageResult))

    api.execute(Methods.sendMessage(ChatIntId(0L), "Lorem ipsum")).unsafeRunSync()
  }

  "should raise errors" in {
    new MockServerClient("localhost", mockServer.getServerPort)
      .when(
        request()
          .withPath("/sendMessage")
          .withMethod("POST")
          .withBody(
            new JsonBody(
              """
                {
                  "chat_id": 0,
                  "text": "Bad request",
                  "method": "sendMessage"
                }
              """
            )
          )
      )
      .respond(response().withBody(errorResult))

    val thrown = the[FailedRequest[Message]] thrownBy api
      .execute(Methods.sendMessage(ChatIntId(0L), "Bad request"))
      .unsafeRunSync()
    thrown.getMessage shouldBe "method=sendMessage code=400 description=Telegram Bot API error"
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
    api.execute(Methods.sendDocument(ChatIntId(0), InputPartFile(file))).unsafeRunSync()
  }

  // Clears earlier tests' requests to the path too, so a test only sees its own.
  private def expectMultipart(path: String, result: JsonBody = messageResult): MockServerClient = {
    val client = new MockServerClient("localhost", mockServer.getServerPort)
    client.clear(request().withPath(path))
    client
      .when(request().withPath(path).withMethod("POST").withHeader("Content-Type", "multipart/form-data.*"))
      .respond(response().withBody(result))
    client
  }

  private def onlyBody(client: MockServerClient, path: String): String = {
    val requests = client.retrieveRecordedRequests(request().withPath(path))
    requests should have size 1
    requests.head.getBodyAsString
  }

  // A multipart part as `Content-Disposition` names it, followed by its content.
  private def part(name: String, content: String): String =
    "(?s).*name=\"" + name + "\".*\r\n\r\n" + java.util.regex.Pattern.quote(content) + "\r\n.*"

  private val streamed = Stream.emits("STREAMED-CONTENT".getBytes).covary[IO]

  "should upload a streamed file as its own part, referenced with attach://" in {
    val client = expectMultipart("/sendDocument")

    api
      .execute(Uploads[IO](attach => Methods.sendDocument(ChatIntId(0), attach("report.pdf", streamed))))
      .unsafeRunSync()

    val body = onlyBody(client, "/sendDocument")
    body should fullyMatch regex part("document", "attach://upload0")
    body should fullyMatch regex part("upload0", "STREAMED-CONTENT")
    body should include("""filename="report.pdf"""")
  }

  "should upload a streamed file and a regular file in the same request" in {
    val client = expectMultipart("/sendDocument")
    val thumb  = new File(this.getClass.getResource("/document.txt").getFile)

    api
      .execute(Uploads[IO] { attach =>
        Methods.sendDocument(ChatIntId(0), attach("report.pdf", streamed), thumbnail = Some(InputPartFile(thumb)))
      })
      .unsafeRunSync()

    val body = onlyBody(client, "/sendDocument")
    body should fullyMatch regex part("upload0", "STREAMED-CONTENT")
    body should include("""name="thumbnail"; filename="document.txt"""")
    // The regular file is a part only, not also a form field.
    """name="thumbnail"""".r.findAllMatchIn(body).size shouldBe 1
  }

  "should upload streamed files nested in a media group" in {
    val client = expectMultipart("/sendMediaGroup", result = new JsonBody("""{"ok": true, "result": []}"""))

    api
      .execute(Uploads[IO] { attach =>
        Methods.sendMediaGroup(
          ChatIntId(0),
          media = List(
            InputMediaDocument(attach("a.csv", Stream.emits("CSV-A".getBytes))),
            InputMediaDocument(attach("b.csv", Stream.emits("CSV-B".getBytes)))
          )
        )
      })
      .unsafeRunSync()

    val body = onlyBody(client, "/sendMediaGroup")
    body should include("attach://upload0")
    body should include("attach://upload1")
    body should fullyMatch regex part("upload0", "CSV-A")
    body should fullyMatch regex part("upload1", "CSV-B")
  }

  "should fail the request if a streamed file's stream fails" in {
    expectMultipart("/sendDocument")
    val failing =
      Stream.emits("PARTIAL".getBytes).covary[IO] ++ Stream.raiseError[IO](new RuntimeException("source failed"))

    val result = api
      .execute(Uploads[IO](attach => Methods.sendDocument(ChatIntId(0), attach("broken.bin", failing))))
      .attempt
      .unsafeRunSync()

    // The stream's own error, not some other failure, e.g. from a request that never carried the stream.
    val causes = Iterator.iterate(result.left.toOption.orNull)(_.getCause).takeWhile(_ != null).map(_.getMessage).toList
    causes should contain("source failed")
  }

  "should send a file by link as JSON" in {
    new MockServerClient("localhost", mockServer.getServerPort)
      .when(
        request()
          .withPath("/sendDocument")
          .withMethod("POST")
          .withBody(
            new JsonBody(
              """
                {
                  "chat_id": 0,
                  "document": "https://example.com/flowers.png"
                }
              """
            )
          )
      )
      .respond(response().withBody(messageResult))

    api.execute(Methods.sendDocument(ChatIntId(0), InputLinkFile("https://example.com/flowers.png"))).unsafeRunSync()
  }

  override protected def afterAll(): Unit = {
    finalizer.unsafeRunSync()
  }

}
