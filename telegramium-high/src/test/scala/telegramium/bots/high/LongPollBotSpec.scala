package telegramium.bots.high

import scala.concurrent.duration.*

import cats.effect.Deferred
import cats.effect.IO
import cats.effect.Ref
import cats.effect.unsafe.implicits.global

import io.circe.DecodingFailure
import io.circe.Json
import io.circe.parser.parse
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import telegramium.bots.Message
import telegramium.bots.Update
import telegramium.bots.client.Method
import telegramium.bots.high.LongPollBot.OffsetKeeper

class LongPollBotSpec extends AnyFreeSpec with Matchers {

  private val updates = parse(
    """
      [
        {"update_id": 1, "some_future_update": {"id": 0}},
        {"update_id": 2, "message": {"message_id": "not a number", "date": 0, "chat": {"id": 0, "type": "private"}}},
        {"update_id": 3, "message": {"message_id": 0, "date": 0, "chat": {"id": 0, "type": "private"}, "text": "hi"}}
      ]
    """
  ).toOption.get

  // Returns the updates for the first getUpdates call and blocks on the following ones
  private val api: Api[IO] = new Api[IO] {
    override def execute[Res](method: Method[Res]): IO[Res] =
      if (method.payload.json.hcursor.get[Int]("offset").contains(0))
        IO.fromEither(method.decoder.decodeJson(updates))
      else IO.never
  }

  "poll should handle each update separately and advance the offset past all of them" in {
    val (offset, events) = (for {
      events        <- Ref.of[IO, List[String]](List.empty)
      currentOffset <- Ref.of[IO, Int](0)
      newOffset     <- Deferred[IO, Int]
      bot = new LongPollBot[IO](api) {
        override def onMessage(msg: Message): IO[Unit] = events.update(s"message ${msg.text.mkString}" :: _)
        override def onUnknownUpdate(update: Update, unknownFields: List[String], json: Json): IO[Unit] =
          events.update(s"unknown ${update.updateId} ${unknownFields.mkString}" :: _)
        override def onError(e: Throwable): IO[Unit] = e match {
          case _: DecodingFailure => events.update("decoding error" :: _)
          case _                  => IO.raiseError(e)
        }
      }
      offsetKeeper = new OffsetKeeper[IO] {
        def getOffset: IO[Int]               = currentOffset.get
        def setOffset(offset: Int): IO[Unit] = currentOffset.set(offset) *> newOffset.complete(offset).void
      }
      offset <- IO.race(bot.poll(offsetKeeper), newOffset.get).timeout(10.seconds)
      events <- events.get
    } yield (offset, events)).unsafeRunSync()

    offset shouldBe Right(4)
    events should contain theSameElementsAs List(
      "unknown 1 some_future_update",
      "decoding error",
      "message hi"
    )
  }

  "poll should still dispatch an update's known fields even if onUnknownUpdate fails" in {
    val update = parse(
      """
        [
          {"update_id": 1, "some_future_update": {"id": 0}, "message": {"message_id": 0, "date": 0, "chat": {"id": 0, "type": "private"}, "text": "hi"}}
        ]
      """
    ).toOption.get

    val api: Api[IO] = new Api[IO] {
      override def execute[Res](method: Method[Res]): IO[Res] =
        if (method.payload.json.hcursor.get[Int]("offset").contains(0))
          IO.fromEither(method.decoder.decodeJson(update))
        else IO.never
    }

    val (offset, events) = (for {
      events        <- Ref.of[IO, List[String]](List.empty)
      currentOffset <- Ref.of[IO, Int](0)
      newOffset     <- Deferred[IO, Int]
      bot = new LongPollBot[IO](api) {
        override def onMessage(msg: Message): IO[Unit] = events.update(s"message ${msg.text.mkString}" :: _)
        override def onUnknownUpdate(update: Update, unknownFields: List[String], json: Json): IO[Unit] =
          IO.raiseError(new RuntimeException("reporting failed"))
        override def onError(e: Throwable): IO[Unit] = events.update(s"error ${e.getMessage}" :: _)
      }
      offsetKeeper = new OffsetKeeper[IO] {
        def getOffset: IO[Int]               = currentOffset.get
        def setOffset(offset: Int): IO[Unit] = currentOffset.set(offset) *> newOffset.complete(offset).void
      }
      offset <- IO.race(bot.poll(offsetKeeper), newOffset.get).timeout(10.seconds)
      events <- events.get
    } yield (offset, events)).unsafeRunSync()

    offset shouldBe Right(2)
    events should contain theSameElementsAs List(
      "error reporting failed",
      "message hi"
    )
  }

  // Returns the "allowed_updates" field of the first getUpdates request the bot sends
  private def firstAllowedUpdates(mkBot: Api[IO] => LongPollBot[IO]): Option[Json] =
    (for {
      request <- Deferred[IO, Json]
      api = new Api[IO] {
        override def execute[Res](method: Method[Res]): IO[Res] =
          if (method.payload.name == "getUpdates") request.complete(method.payload.json) *> IO.never
          else IO.fromEither(method.decoder.decodeJson(Json.True))
      }
      json <- IO.race(mkBot(api).start(), request.get).timeout(10.seconds)
    } yield json.toOption.get.hcursor.downField("allowed_updates").focus).unsafeRunSync()

  // https://github.com/apimorphism/telegramium/issues/686: an empty allowed_updates resets Telegram's
  // filter to the default, which leaves out chat_member and reaction updates, on every poll
  "getUpdates should leave out allowed_updates by default, so Telegram keeps the previous setting" in {
    firstAllowedUpdates(api => new LongPollBot[IO](api) {}) shouldBe None
  }

  "getUpdates should send allowedUpdates when overridden" in {
    firstAllowedUpdates { api =>
      new LongPollBot[IO](api) {
        override def allowedUpdates: Option[List[String]] = Some(List("message", "chat_member"))
      }
    } shouldBe Some(Json.arr(Json.fromString("message"), Json.fromString("chat_member")))
  }

}
