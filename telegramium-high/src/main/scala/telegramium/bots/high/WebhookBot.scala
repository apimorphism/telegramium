package telegramium.bots.high

import cats.effect.{ConcurrentEffect, Resource, Sync, Timer}
import cats.implicits._
import io.circe.syntax._
import org.http4s.circe.{jsonOf, _}
import org.http4s.dsl.Http4sDsl
import org.http4s.dsl.impl.Path
import org.http4s.implicits._
import org.http4s.server.Server
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.{EntityDecoder, HttpApp, HttpRoutes}
import telegramium.bots.CirceImplicits._
import telegramium.bots.client.{Api, SetWebhookReq}
import telegramium.bots.{CallbackQuery, ChosenInlineResult, HandleUpdateReq, InlineQuery, InputPartFile, Message}

/**
 * @param url            HTTPS url to send updates to. Use an empty string to remove webhook integration
 * @param path           Webhook route. Must be consistent with `url`.
 * @param certificate    Upload your public key certificate so that the root certificate in use can be checked.
 * @param maxConnections Maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery, 1-100.
 *                       Defaults to 40. Use lower values to limit the load on your bot‘s server, and higher values
 *                       to increase your bot’s throughput.
 * @param allowedUpdates A list of the update types you want your bot to receive.
 *                       For example, specify [“message”, “edited_channel_post”, “callback_query”] to only receive
 *                       updates of these types. See Update for a complete list of available update types.
 *                       Specify an empty list to receive all updates regardless of type (default).
 *                       If not specified, the previous setting will be used. Please note that this parameter doesn't
 *                       affect updates created before the call to the setWebhook, so unwanted updates may be received
 *                       for a short period of time.
 */
abstract class WebhookBot[F[_]: ConcurrentEffect](
  bot: Api[F],
  port: Int,
  url: String,
  path: String = "/",
  certificate: Option[InputPartFile] = Option.empty,
  maxConnections: Option[Int] = Option.empty,
  allowedUpdates: List[String] = List.empty
)(implicit syncF: Sync[F], timer: Timer[F]) {

  private val BotPath = Path(if (path.startsWith("/")) path else path.prepended('/'))

  def onMessage(msg: Message): F[BotApiMethod[_]] = ???
  def onInlineQuery(query: InlineQuery): F[BotApiMethod[_]] = ???
  def onCallbackQuery(query: CallbackQuery): F[BotApiMethod[_]] = ???
  def onChosenInlineResult(inlineResult: ChosenInlineResult): F[BotApiMethod[_]] = ???

  def onUpdate(update: HandleUpdateReq): F[Option[BotApiMethod[_]]] = update.message.traverse(onMessage)

  private implicit val HandleUpdateReqEntityDecoder: EntityDecoder[F, HandleUpdateReq] = jsonOf[F, HandleUpdateReq]

  def handleUpdateReq(rawReq: org.http4s.Request[F]): F[BotApiMethod[_]] =
    rawReq.as[HandleUpdateReq].flatMap(onUpdate(_).map(_.get))

  def start(): Resource[F, Server[F]] = setWebhook(url, certificate, maxConnections, allowedUpdates) *> createServer()

  private def setWebhook(
    url: String,
    certificate: Option[InputPartFile],
    maxConnections: Option[Int],
    allowedUpdates: List[String]
  ): Resource[F, Unit] =
    Resource.make(
      bot.setWebhook(SetWebhookReq(url, certificate, maxConnections, allowedUpdates)).void
    )(
      _ => bot.deleteWebhook().void
    )

  import telegramium.bots.high.SendMessage.sendmessageEncoder

  private def createServer(): Resource[F, Server[F]] = {
    val dsl = Http4sDsl[F]
    import dsl._

    def app(): HttpApp[F] =
      HttpRoutes.of[F] {
        case req @ POST -> BotPath =>
          handleUpdateReq(req).flatMap {
            case x: SendMessage => Ok(x.asJson)
          }
      }
        .orNotFound

    BlazeServerBuilder[F].bindHttp(port).withHttpApp(app()).resource
  }

}
