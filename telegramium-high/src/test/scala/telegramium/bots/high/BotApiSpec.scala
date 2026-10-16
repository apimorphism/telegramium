package telegramium.bots.high

import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicBoolean
import scala.concurrent.duration.*

import cats.effect.IO
import cats.effect.unsafe.implicits.global

import com.dimafeng.testcontainers.ForAllTestContainer
import com.dimafeng.testcontainers.MockServerContainer
import fs2.interop.reactivestreams.fromPublisher
import iozhik.NamedInputStream
import org.http4s.ember.client.EmberClientBuilder
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import org.mockserver.model.JsonBody
import org.reactivestreams.Publisher
import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.Eventually
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.Seconds
import org.scalatest.time.Span
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

class BotApiSpec extends AnyFreeSpec with ForAllTestContainer with BeforeAndAfterAll with Matchers with Eventually {
  lazy val container: MockServerContainer = MockServerContainer(MockServerContainerVersion)
  private val mockServer                  = container.container

  private val (httpClient, finalizer) = EmberClientBuilder.default[IO].build.allocated.unsafeRunSync()
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
  // (bridged via InputStreamFiles.fromStream), and a ZIO ZStream (bridged via the Reactive
  // Streams protocol: zio-interop-reactivestreams' toPublisher -> fs2's fromPublisher, then
  // InputStreamFiles.fromStream). They check that the bytes actually reach Telegram as the
  // request body, and that canceling the request midway releases the caller's source.

  private val documentContent = "Lorem ipsum dolor sit amet"

  // Clears earlier tests' /sendDocument requests too, so each test only sees its own.
  private def expectMultipartSendDocument(): Unit = {
    val client = new MockServerClient("localhost", mockServer.getServerPort)
    client.clear(request().withPath("/sendDocument"))
    client
      .when(
        request()
          .withPath("/sendDocument")
          .withMethod("POST")
          .withHeader("Content-Type", "multipart/form-data.*")
      )
      .respond(response().withBody(messageResult))
  }

  private def onlySendDocumentBody(): String = {
    val requests = new MockServerClient("localhost", mockServer.getServerPort)
      .retrieveRecordedRequests(request().withPath("/sendDocument"))
    requests should have size 1
    requests.head.getBodyAsString
  }

  private def partsNamed(body: String, name: String): Int =
    s"""name="$name"""".r.findAllMatchIn(body).size

  private def zioPublisher(zstream: ZStream[Any, Throwable, Byte]): IO[Publisher[Byte]] =
    IO.blocking(Unsafe.unsafe(implicit u => Runtime.default.unsafe.run(zstream.toPublisher).getOrThrowFiberFailure()))

  // A source that never ends on its own, so the upload only stops when the request is canceled.
  private def endless[A](a: A): fs2.Stream[IO, A] = fs2.Stream.awakeEvery[IO](10.millis).as(a)

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

    val body = onlySendDocumentBody()
    body should include(documentContent)
    partsNamed(body, "document") shouldBe 1
    closed.get() shouldBe true
  }

  "should send an InputPartFile and an InputStreamFile in the same request" in {
    expectMultipartSendDocument()
    val thumbnail = new File(this.getClass.getResource("/document.txt").getFile)
    val document  = NamedInputStream("stream.bin", () => new ByteArrayInputStream("STREAM-CONTENT".getBytes))

    api
      .execute(
        Methods.sendDocument(ChatIntId(0), InputStreamFile(document), thumbnail = Option(InputPartFile(thumbnail)))
      )
      .unsafeRunSync()

    val body = onlySendDocumentBody()
    body should include("STREAM-CONTENT")
    body should include(documentContent)
    partsNamed(body, "document") shouldBe 1
    partsNamed(body, "thumbnail") shouldBe 1
  }

  "should send a file from an fs2.Stream[F, Byte]" in {
    expectMultipartSendDocument()
    val content = "FS2-STREAM-CONTENT"

    InputStreamFiles
      .fromStream("fs2.bin", fs2.Stream.emits(content.getBytes).covary[IO])
      .use(file => api.execute(Methods.sendDocument(ChatIntId(0), file)))
      .unsafeRunSync()

    onlySendDocumentBody() should include(content)
  }

  "should finalize an fs2.Stream[F, Byte] before returning, if the request is canceled mid-upload" in {
    expectMultipartSendDocument()
    val finalized = new AtomicBoolean(false)
    // A slow finalizer, so this only passes if canceling waits for it rather than just triggering it.
    val stream = endless('x'.toByte).onFinalize(IO.sleep(200.millis) >> IO(finalized.set(true)))

    val result = InputStreamFiles
      .fromStream("endless.bin", stream)
      .use(file => api.execute(Methods.sendDocument(ChatIntId(0), file)).timeout(1.second).attempt)
      .unsafeRunSync()

    result.left.map(_.getClass) shouldBe Left(classOf[TimeoutException])
    finalized.get() shouldBe true
  }

  "should send a file from a ZIO ZStream" in {
    expectMultipartSendDocument()
    val content = "ZIO-STREAM-CONTENT"

    zioPublisher(ZStream.fromIterable(content.getBytes))
      .flatMap { publisher =>
        InputStreamFiles
          .fromStream("zio.bin", fromPublisher[IO, Byte](publisher, bufferSize = 16))
          .use(file => api.execute(Methods.sendDocument(ChatIntId(0), file)))
      }
      .unsafeRunSync()

    onlySendDocumentBody() should include(content)
  }

  "should finalize a ZIO ZStream, if the request is canceled mid-upload" in {
    expectMultipartSendDocument()
    val finalized = new AtomicBoolean(false)
    val zstream   = ZStream
      .tick(zio.Duration.fromMillis(10))
      .as('x'.toByte)
      .ensuring(ZIO.succeed(finalized.set(true)))

    val result = zioPublisher(zstream)
      .flatMap { publisher =>
        InputStreamFiles
          .fromStream("endless.bin", fromPublisher[IO, Byte](publisher, bufferSize = 16))
          .use(file => api.execute(Methods.sendDocument(ChatIntId(0), file)).timeout(1.second).attempt)
      }
      .unsafeRunSync()

    result.left.map(_.getClass) shouldBe Left(classOf[TimeoutException])
    // Reactive Streams cancellation is only a signal: ZIO interrupts the producing fiber asynchronously.
    eventually(timeout(Span(5, Seconds)))(finalized.get() shouldBe true)
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
