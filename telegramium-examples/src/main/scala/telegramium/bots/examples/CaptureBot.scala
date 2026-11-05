package telegramium.bots.examples

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import scala.concurrent.duration.*

import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.IOApp
import cats.effect.Ref
import cats.syntax.all.*

import io.circe.Json
import org.http4s.Method.POST
import org.http4s.Request
import org.http4s.Uri
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.circe.*
import org.http4s.client.Client

/** Records the raw JSON Telegram sends the live-test bot, for FixtureScrubber to turn into telegramium-core's fixtures:
  * every update, and the response to getMe, and to getChat, getChatAdministrators, getUserProfilePhotos and getFile for
  * each chat, user and file the updates mention. Raw, rather than through BotApi, so that fields telegramium doesn't
  * know yet are kept. On /interact in a private chat it sends an inline keyboard and a poll, and records those
  * responses too, so that tapping a button and voting give callback_query, poll and poll_answer updates. It answers
  * inline queries with one article, so that picking it gives chosen_inline_result (with inline feedback on). See
  * CONTRIBUTING.md on refreshing the fixtures.
  *
  * Usage: CaptureBot [rawDir], with TELEGRAM_LIVE_BOT_TOKEN set. rawDir defaults to .fixtures-raw, which is gitignored:
  * what it holds is not scrubbed yet.
  */
object CaptureBot extends IOApp {

  // every update kind, as plain getUpdates leaves out chat_member, message_reaction and message_reaction_count
  private val allowedUpdates = List(
    "message",
    "edited_message",
    "channel_post",
    "edited_channel_post",
    "business_connection",
    "business_message",
    "edited_business_message",
    "deleted_business_messages",
    "guest_message",
    "message_reaction",
    "message_reaction_count",
    "inline_query",
    "chosen_inline_result",
    "callback_query",
    "shipping_query",
    "pre_checkout_query",
    "purchased_paid_media",
    "poll",
    "poll_answer",
    "my_chat_member",
    "chat_member",
    "chat_join_request",
    "chat_boost",
    "removed_chat_boost",
    "managed_bot",
    "subscription",
    "stopped_message_generation"
  )

  def run(args: List[String]): IO[ExitCode] = for {
    token <- IO.fromOption(sys.env.get("TELEGRAM_LIVE_BOT_TOKEN").filter(_.nonEmpty))(
      new RuntimeException("TELEGRAM_LIVE_BOT_TOKEN is not set")
    )
    raw = Paths.get(args.headOption.getOrElse(".fixtures-raw"))
    // well above getUpdates' 30 s long poll
    client = BlazeClientBuilder[IO].withRequestTimeout(60.seconds).withIdleTimeout(90.seconds)
    _ <- client.resource.use(http => new Capture(http, token, raw).run)
  } yield ExitCode.Success

  private class Capture(http: Client[IO], token: String, raw: Path) {

    private val api = Uri.unsafeFromString(s"https://api.telegram.org/bot$token")

    // the `result` of a Bot API call, as Telegram sent it
    private def call(method: String, params: (String, Json)*): IO[Json] =
      http
        .expect[Json](Request[IO](POST, api / method).withEntity(Json.fromFields(params)))
        .flatMap { response =>
          val c = response.hcursor
          if (c.get[Boolean]("ok").contains(true)) IO.fromEither(c.downField("result").as[Json])
          else IO.raiseError(new RuntimeException(s"$method failed: ${response.noSpaces}"))
        }

    private def write(path: Path, json: Json): IO[Unit] = IO {
      Files.createDirectories(path.getParent)
      Files.write(path, json.spaces2.getBytes(StandardCharsets.UTF_8))
    }.void

    // records one response, skipping calls Telegram refuses, such as getChatAdministrators on a private chat
    private def record(method: String, key: String, params: (String, Json)*): IO[Unit] =
      call(method, params*)
        .flatMap(write(raw.resolve("responses").resolve(method).resolve(s"$key.json"), _))
        .handleErrorWith(e => IO.println(s"Skipped $method($key): ${e.getMessage}"))

    private def recordChat(id: Long, isPrivate: Boolean): IO[Unit] =
      record("getChat", id.toString, "chat_id" -> Json.fromLong(id)) >>
        record("getChatAdministrators", id.toString, "chat_id" -> Json.fromLong(id)).unlessA(isPrivate)

    private def recordUser(id: Long): IO[Unit] =
      record("getUserProfilePhotos", id.toString, "user_id" -> Json.fromLong(id))

    private def recordFile(id: String): IO[Unit] =
      record("getFile", id, "file_id" -> Json.fromString(id))

    // every value under `key` anywhere in the update
    private def findAll(json: Json, key: String): List[Json] =
      json.arrayOrObject(
        List.empty,
        _.toList.flatMap(findAll(_, key)),
        obj => obj.toList.flatMap { case (k, v) => (if (k == key) List(v) else List.empty) ++ findAll(v, key) }
      )

    def run: IO[Unit] = for {
      _    <- record("getMe", "me")
      seen <- Ref[IO].of(Set.empty[String])
      // the first time a chat, user or file turns up
      once = (key: String, action: IO[Unit]) => seen.modify(s => (s + key, s(key))).flatMap(action.unlessA)
      _ <- IO.println(s"Recording to $raw. Stop with Ctrl-C.")
      _ <- poll(
        0L,
        update => {
          val chats = (findAll(update, "chat") ++ findAll(update, "sender_chat")).distinct
          val users = findAll(update, "from").flatMap(_.hcursor.get[Long]("id").toOption).distinct
          val files = findAll(update, "file_id").flatMap(_.asString).distinct
          chats.traverse_ { chat =>
            val id = chat.hcursor.get[Long]("id").toOption.get
            once(s"chat:$id", recordChat(id, chat.hcursor.get[String]("type").contains("private")))
          } >>
            users.traverse_(id => once(s"user:$id", recordUser(id))) >>
            files.traverse_(id => once(s"file:$id", recordFile(id))) >>
            interact(update)
        }
      )
    } yield ()

    // updates only the bot's own messages can produce: a tap on an inline button, and a vote in a non-anonymous poll
    private def interact(update: Json): IO[Unit] = {
      val message  = update.hcursor.downField("message")
      val callback = update.hcursor.downField("callback_query").get[String]("id").toOption
      val inline   = update.hcursor.downField("inline_query").get[String]("id").toOption
      (message.get[String]("text").toOption, message.downField("chat").get[Long]("id").toOption) match {
        case (Some("/interact"), Some(chatId)) =>
          val button = (text: String) =>
            Json.obj("text" -> Json.fromString(text), "callback_data" -> Json.fromString(text))
          record(
            "sendMessage",
            chatId.toString,
            "chat_id"      -> Json.fromLong(chatId),
            "text"         -> Json.fromString("Tap a button"),
            "reply_markup" -> Json.obj("inline_keyboard" -> Json.arr(Json.arr(button("One"), button("Two"))))
          ) >> record(
            "sendPoll",
            chatId.toString,
            "chat_id"  -> Json.fromLong(chatId),
            "question" -> Json.fromString("Vote"),
            "options"  -> Json.arr(Json.obj("text" -> Json.fromString("A")), Json.obj("text" -> Json.fromString("B"))),
            "is_anonymous" -> Json.False
          )
        case _ =>
          // stops the button's loading spinner
          callback.traverse_(id =>
            call("answerCallbackQuery", "callback_query_id" -> Json.fromString(id)).attempt.void
          ) >>
            inline.traverse_ { id =>
              val article = Json.obj(
                "type"                  -> Json.fromString("article"),
                "id"                    -> Json.fromString("article"),
                "title"                 -> Json.fromString("Send this"),
                "input_message_content" -> Json.obj("message_text" -> Json.fromString("Sent inline"))
              )
              call(
                "answerInlineQuery",
                "inline_query_id" -> Json.fromString(id),
                "results"         -> Json.arr(article),
                "cache_time"      -> Json.fromInt(0)
              ).void.handleErrorWith(e => IO.println(s"answerInlineQuery failed: ${e.getMessage}"))
            }
      }
    }

    private def poll(offset: Long, onUpdate: Json => IO[Unit]): IO[Unit] =
      call(
        "getUpdates",
        "offset"          -> Json.fromLong(offset),
        "timeout"         -> Json.fromInt(30),
        "allowed_updates" -> Json.fromValues(allowedUpdates.map(Json.fromString))
      ).flatMap { result =>
        val updates = result.asArray.toList.flatten.map(u => u.hcursor.get[Long]("update_id").toOption.get -> u)
        updates
          .traverse_ { case (id, update) =>
            write(raw.resolve("updates").resolve(s"$id.json"), update) >>
              IO.println(s"Recorded update $id: ${update.asObject.toList.flatMap(_.keys).mkString(", ")}") >>
              onUpdate(update)
          }
          .as(updates.map(_._1).maxOption.fold(offset)(_ + 1))
      }.handleErrorWith { e =>
        // a dropped connection mustn't end a recording session; Telegram keeps the updates until they're confirmed
        IO.println(s"getUpdates failed, retrying: ${e.getMessage}") >> IO.sleep(5.seconds).as(offset)
      }.flatMap(poll(_, onUpdate))

  }

}
