package telegramium.bots.examples

import scala.util.Random

import cats.Parallel
import cats.effect.Async
import cats.syntax.all.*

import iozhik.OpenEnum
import org.http4s.client.Client

import telegramium.bots.ChatIntId
import telegramium.bots.Message
import telegramium.bots.UrlMessageEntity
import telegramium.bots.high.Api
import telegramium.bots.high.LongPollBot
import telegramium.bots.high.implicits.*

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
      url <- msg.entities.collectFirst { case OpenEnum.Known(UrlMessageEntity(offset, length)) =>
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
