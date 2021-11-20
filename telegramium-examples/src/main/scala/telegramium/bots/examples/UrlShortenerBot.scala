package telegramium.bots.examples

import cats.Parallel
import cats.effect.Async
import cats.syntax.all.*
import org.http4s.client.Client
import telegramium.bots.{ChatIntId, Message, UrlMessageEntity}
import telegramium.bots.high.{Api, LongPollBot}
import telegramium.bots.high.implicits.*

import scala.util.Random

/** Scala 3 example
  */
class UrlShortenerBot[F[_]](httpClient: Client[F])(using botApi: Api[F], asyncF: Async[F], parallel: Parallel[F])
    extends LongPollBot[F](botApi):

  private val random = Random()

  private enum Provider:
    case Tinyurl, Vgd

  override def onMessage(msg: Message): F[Unit] =
    val reply = for {
      text <- msg.text
      url <- msg.entities.collectFirst { case UrlMessageEntity(offset, length) =>
        text.slice(offset, offset + length)
      }
    } yield {
      shorten(url)
        .flatMap { shortUrl =>
          sendMessage(
            chatId = ChatIntId(msg.chat.id),
            text = shortUrl
          ).exec.void
        }
    }

    reply.getOrElse(asyncF.unit)

  private def createRequestUrl(url: String, provider: Provider): String =
    provider match {
      case Provider.Tinyurl => s"https://tinyurl.com/api-create.php?url=$url"
      case Provider.Vgd     => s"https://v.gd/create.php?format=simple&url=$url"
    }

  private def shorten(url: String) =
    httpClient.expect[String](createRequestUrl(url, Provider.fromOrdinal(random.nextInt(2))))
