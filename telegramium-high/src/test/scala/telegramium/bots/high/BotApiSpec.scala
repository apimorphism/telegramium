package telegramium.bots.high

import java.io.File
import java.io.FileInputStream
import java.util.concurrent.atomic.AtomicBoolean

import cats.effect.IO
import cats.effect.std.Dispatcher
import cats.effect.unsafe.implicits.global

import com.dimafeng.testcontainers.ForAllTestContainer
import com.dimafeng.testcontainers.MockServerContainer
import fs2.interop.reactivestreams.fromPublisher
import iozhik.NamedInputStream
import org.http4s.blaze.client.BlazeClientBuilder
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import org.mockserver.model.JsonBody
import org.reactivestreams.Publisher
import org.scalatest.BeforeAndAfterAll
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import zio.Runtime
import zio.Unsafe
import zio.ZIO
import zio.interop.reactivestreams.*
import zio.stream.ZStream

import telegramium.bots.ChatIntId
import telegramium.bots.InputLinkFile
import telegramium.bots.InputPartFile
import telegramium.bots.InputStreamFile
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

  // The scenarios below each represent a different kind of stream a caller might hand to
  // InputStreamFile: a plain java.io.InputStream (no bridging needed), an fs2.Stream[F, Byte]
  // (bridged via fs2's own toInputStreamResource), and a ZIO ZStream (bridged via the Reactive
  // Streams protocol: zio-interop-reactivestreams' toPublisher -> fs2's fromPublisher). For each,
  // we check both halves of the contract: the bytes actually reach Telegram as the request body,
  // and the caller's resource gets closed once the request completes.

  private def expectMultipartSendDocument(): Unit =
    new MockServerClient("localhost", mockServer.getServerPort)
      .when(
        request()
          .withPath("/sendDocument")
          .withMethod("POST")
          .withHeader("Content-Type", "multipart/form-data.*")
      )
      .respond(response().withBody(messageResult))

  private def lastSendDocumentBody(): String =
    new MockServerClient("localhost", mockServer.getServerPort)
      .retrieveRecordedRequests(request().withPath("/sendDocument"))
      .last
      .getBodyAsString

  "should send a file from a plain java.io.InputStream and close it once the request completes" in {
    expectMultipartSendDocument()
    val closed = new AtomicBoolean(false)
    val file   = new File(this.getClass.getResource("/document.txt").getFile)
    val named  = NamedInputStream(
      "document.txt",
      () =>
        new FileInputStream(file) {
          override def close(): Unit = { super.close(); closed.set(true) }
        }
    )

    api.execute(Methods.sendDocument(ChatIntId(0), InputStreamFile(named))).unsafeRunSync()

    lastSendDocumentBody() should include("document.txt")
    closed.get() shouldBe true
  }

  "should send a file from an fs2.Stream[F, Byte], bridged via fs2.io.toInputStreamResource, and close it once the request completes" in {
    expectMultipartSendDocument()
    val closed                       = new AtomicBoolean(false)
    val content                      = "FS2-STREAM-CONTENT"
    val stream: fs2.Stream[IO, Byte] =
      fs2.Stream.emits(content.getBytes).covary[IO].onFinalize(IO(closed.set(true)))

    Dispatcher
      .sequential[IO]
      .use { dispatcher =>
        val named = NamedInputStream(
          "fs2.bin",
          () => dispatcher.unsafeRunSync(fs2.io.toInputStreamResource(stream).allocated.map(_._1))
        )
        api.execute(Methods.sendDocument(ChatIntId(0), InputStreamFile(named)))
      }
      .unsafeRunSync()

    lastSendDocumentBody() should include(content)
    closed.get() shouldBe true
  }

  "should send a file from a ZIO ZStream, bridged via Reactive Streams interop, and close it once the request completes" in {
    expectMultipartSendDocument()
    val closed                                 = new AtomicBoolean(false)
    val content                                = "ZIO-STREAM-CONTENT"
    val zstream: ZStream[Any, Throwable, Byte] =
      ZStream.fromIterable(content.getBytes).ensuring(ZIO.succeed(closed.set(true)))

    Dispatcher
      .sequential[IO]
      .use { dispatcher =>
        IO {
          val publisher: Publisher[Byte] =
            Unsafe.unsafe(implicit u => Runtime.default.unsafe.run(zstream.toPublisher).getOrThrowFiberFailure())
          val fs2Stream: fs2.Stream[IO, Byte] = fromPublisher[IO, Byte](publisher, bufferSize = 16)
          val named                           = NamedInputStream(
            "zio.bin",
            () => dispatcher.unsafeRunSync(fs2.io.toInputStreamResource(fs2Stream).allocated.map(_._1))
          )
          api.execute(Methods.sendDocument(ChatIntId(0), InputStreamFile(named))).unsafeRunSync()
        }
      }
      .unsafeRunSync()

    lastSendDocumentBody() should include(content)
    closed.get() shouldBe true
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
