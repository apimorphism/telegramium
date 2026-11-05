package telegramium.bots.high

import java.io.File as JFile
import java.nio.charset.StandardCharsets
import java.nio.file.Files

import scala.concurrent.duration.*

import cats.effect.Deferred
import cats.effect.IO
import cats.effect.Ref
import cats.effect.unsafe.implicits.global

import io.circe.Json
import io.circe.parser.parse
import org.http4s.Method.POST
import org.http4s.Request
import org.http4s.Status
import org.http4s.circe.*
import org.http4s.implicits.*
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import telegramium.bots.Update
import telegramium.bots.client.Method
import telegramium.bots.high.LongPollBot.OffsetKeeper

/** Both bots survive updates from a Bot API release newer than the library: the future fixtures from telegramium-core's
  * tests are acknowledged, and their unknown top-level fields reported through onUnknownUpdate.
  */
class FutureUpdatesSpec extends AnyFreeSpec with Matchers {

  // the top-level fields onUnknownUpdate should report for each fixture; fields nested inside known objects aren't
  private val expectedUnknownFields: Map[String, List[String]] = Map(
    "unknown_top_level_field-001.json" -> List("future_field"),
    "unknown_update_kind-001.json"     -> List("future_update"),
    "unknown_value-001.json"           -> List.empty,
    "unknown_value-002.json"           -> List.empty,
    "unknown_nested_fields-001.json"   -> List.empty
  )

  private val fixtures: List[(String, Json)] =
    Option(getClass.getResource("/fixtures/future"))
      .map(url => new JFile(url.toURI).listFiles().toList.filter(_.getName.endsWith(".json")).sortBy(_.getName))
      .getOrElse(List.empty)
      .map { file =>
        val raw = new String(Files.readAllBytes(file.toPath), StandardCharsets.UTF_8)
        file.getName -> parse(raw).fold(e => fail(e.getMessage), json => json)
      }

  // the onUnknownUpdate calls expected for a fixture: one if it has unknown top-level fields, none otherwise
  private def expectedOnUnknownUpdateCalls(name: String): List[List[String]] =
    List(expectedUnknownFields(name)).filter(_.nonEmpty)

  private def updateId(json: Json): Int = json.hcursor.get[Int]("update_id").fold(e => fail(e.getMessage), id => id)

  "every future fixture has its expected unknown fields listed" in {
    fixtures.map(_._1) should contain theSameElementsAs expectedUnknownFields.keys
  }

  "LongPollBot" - {
    fixtures.foreach { case (name, json) =>
      s"moves past $name, reports its unknown top-level fields and keeps polling" in {
        val (nextOffset, calls, errors) = (for {
          calls      <- Ref.of[IO, List[List[String]]](List.empty)
          errors     <- Ref.of[IO, List[Throwable]](List.empty)
          offset     <- Ref.of[IO, Int](0)
          nextOffset <- Deferred[IO, Int]
          // returns the fixture for the first getUpdates call and records the offset of the next one
          api = new Api[IO] {
            override def execute[Res](method: Method[Res]): IO[Res] =
              method.payload.json.hcursor.get[Int]("offset") match {
                case Right(0)      => IO.fromEither(method.decoder.decodeJson(Json.arr(json)))
                case Right(offset) => nextOffset.complete(offset) *> IO.never
                case Left(e)       => IO.raiseError(e)
              }
          }
          bot = new LongPollBot[IO](api) {
            override def onUnknownUpdate(update: Update, unknownFields: List[String], json: Json): IO[Unit] =
              calls.update(unknownFields :: _)
            override def onError(e: Throwable): IO[Unit] = errors.update(e :: _)
          }
          offsetKeeper = new OffsetKeeper[IO] {
            def getOffset: IO[Int]          = offset.get
            def setOffset(o: Int): IO[Unit] = offset.set(o)
          }
          nextOffset <- IO.race(bot.poll(offsetKeeper), nextOffset.get).timeout(10.seconds)
          calls      <- calls.get
          errors     <- errors.get
        } yield (nextOffset, calls, errors)).unsafeRunSync()

        errors shouldBe List.empty
        nextOffset shouldBe Right(updateId(json) + 1)
        calls shouldBe expectedOnUnknownUpdateCalls(name)
      }
    }
  }

  "WebhookBot" - {
    // the bot never calls the API here: routes() doesn't set the webhook
    val api: Api[IO] = new Api[IO] {
      override def execute[Res](method: Method[Res]): IO[Res] = IO.raiseError(new UnsupportedOperationException)
    }

    fixtures.foreach { case (name, json) =>
      s"acknowledges $name and reports its unknown top-level fields" in {
        val (status, calls) = (for {
          calls <- Ref.of[IO, List[List[String]]](List.empty)
          bot = new WebhookBot[IO](api, url = "localhost") {
            override def onUnknownUpdate(update: Update, unknownFields: List[String], json: Json): IO[Unit] =
              calls.update(unknownFields :: _)
          }
          response <- bot.routes().orNotFound.run(Request[IO](POST, uri"/").withEntity(json))
          calls    <- calls.get
        } yield (response.status, calls)).unsafeRunSync()

        status shouldBe Status.Ok
        calls shouldBe expectedOnUnknownUpdateCalls(name)
      }
    }
  }

}
