package telegramium.bots.high

import java.io.File

import cats.effect.IO
import cats.effect.unsafe.implicits.global

import com.dimafeng.testcontainers.ForAllTestContainer
import com.dimafeng.testcontainers.MockServerContainer
import fs2.Stream
import io.circe.parser.parse
import org.http4s.Headers
import org.http4s.Request
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.multipart.Multipart
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import org.mockserver.model.JsonBody
import org.scalatest.BeforeAndAfterAll
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import telegramium.bots.ChatIntId
import telegramium.bots.InputLinkFile
import telegramium.bots.InputPartFile
import telegramium.bots.Message
import telegramium.bots.ReplyParameters
import telegramium.bots.high.keyboards.InlineKeyboardButtons
import telegramium.bots.high.keyboards.InlineKeyboardMarkups

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

  "should send object-valued fields of a file upload as JSON form fields" in {
    val client = new MockServerClient("localhost", mockServer.getServerPort)
    client
      .when(request().withPath("/sendPhoto").withMethod("POST"))
      .respond(response().withBody(messageResult))
    val file = new File(this.getClass.getResource("/document.txt").getFile)
    api
      .execute(
        Methods.sendPhoto(
          ChatIntId(0),
          InputPartFile(file),
          caption = Some("hi"),
          replyParameters = Some(ReplyParameters(messageId = Some(1))),
          replyMarkup = Some(InlineKeyboardMarkups.singleButton(InlineKeyboardButtons.callbackData("Press", "pressed")))
        )
      )
      .unsafeRunSync()

    val recorded  = client.retrieveRecordedRequests(request().withPath("/sendPhoto")).toList
    val multipart = recorded.map { r =>
      Request[IO](headers = Headers("Content-Type" -> r.getFirstHeader("Content-Type")))
        .withBodyStream(Stream.emits(r.getBodyAsRawBytes.toSeq))
        .as[Multipart[IO]]
        .unsafeRunSync()
    }
    multipart.map(MultipartFields.fileParts) shouldBe List(Vector("photo" -> "document.txt"))
    val fields = MultipartFields.formFields(multipart.head).unsafeRunSync()
    fields("caption") shouldBe "hi"
    parse(fields("reply_parameters")) shouldBe parse("""{"message_id": 1}""")
    parse(fields("reply_markup")) shouldBe parse(
      """{"inline_keyboard": [[{"text": "Press", "callback_data": "pressed"}]]}"""
    )
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
