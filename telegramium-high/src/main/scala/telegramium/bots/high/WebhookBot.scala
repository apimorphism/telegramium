package telegramium.bots.high

import cats.Monad
import cats.effect.{Blocker, ConcurrentEffect, ContextShift, Resource, Timer}
import cats.syntax.apply._
import cats.syntax.flatMap._
import cats.syntax.functor._
import org.http4s.circe.{jsonOf, _}
import org.http4s.dsl.Http4sDsl
import org.http4s.dsl.impl.Path
import org.http4s.implicits._
import org.http4s.server.Server
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.{EntityDecoder, HttpApp, HttpRoutes}
import telegramium.bots.CirceImplicits._
import telegramium.bots.client.{Method, MethodReq, Methods}
import telegramium.bots.high.Http4sUtils.{toFileDataParts, toMultipartWithFormData}
import telegramium.bots.{CallbackQuery, ChosenInlineResult, InlineQuery, InputPartFile, Message, Update}

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
abstract class WebhookBot[F[_]: ConcurrentEffect: ContextShift](
  bot: Api[F],
  port: Int,
  url: String,
  path: String = "/",
  blocker: Blocker = DefaultBlocker.blocker,
  certificate: Option[InputPartFile] = Option.empty,
  maxConnections: Option[Int] = Option.empty,
  allowedUpdates: List[String] = List.empty
)(implicit timer: Timer[F]) extends Methods {

  private val BotPath = Path(if (path.startsWith("/")) path else path.prepended('/'))

  def onMessage(msg: Message): F[Unit] = Monad[F].unit
  def onInlineQuery(query: InlineQuery): F[Unit] = Monad[F].unit
  def onCallbackQuery(query: CallbackQuery): F[Unit] = Monad[F].unit
  def onChosenInlineResult(inlineResult: ChosenInlineResult): F[Unit] = Monad[F].unit

  def onMessageReply(msg: Message): F[Option[Method[_]]] = Monad[F].pure(Option.empty[Method[_]])
  def onInlineQueryReply(query: InlineQuery): F[Option[Method[_]]] = Monad[F].pure(Option.empty[Method[_]])
  def onCallbackQueryReply(query: CallbackQuery): F[Option[Method[_]]] = Monad[F].pure(Option.empty[Method[_]])
  def onChosenInlineResultReply(inlineResult: ChosenInlineResult): F[Option[Method[_]]] = Monad[F].pure(Option.empty[Method[_]])

  def onUpdate(update: Update): F[Option[Method[_]]] =
    List(
      update.message.map { message =>
        onMessageReply(message) <* onMessage(message)
      },
      update.inlineQuery.map { query =>
        onInlineQueryReply(query) <* onInlineQuery(query)
      },
      update.callbackQuery.map { query =>
        onCallbackQueryReply(query) <* onCallbackQuery(query)
      },
      update.chosenInlineResult.map { inlineResult =>
        onChosenInlineResultReply(inlineResult) <* onChosenInlineResult(inlineResult)
      }
    )
      .flatten
      .head

  private implicit val HandleUpdateReqEntityDecoder: EntityDecoder[F, Update] = jsonOf[F, Update]

  private def handleUpdateReq(rawReq: org.http4s.Request[F]): F[Option[Method[_]]] = rawReq.as[Update].flatMap(onUpdate)

  def start(): Resource[F, Server[F]] = setWebhook(url, certificate, maxConnections, allowedUpdates) *> createServer()

  private def setWebhook(
    url: String,
    certificate: Option[InputPartFile],
    maxConnections: Option[Int],
    allowedUpdates: List[String]
  ): Resource[F, Unit] =
    Resource.make(
      bot.execute(setWebhook(url, certificate, maxConnections, allowedUpdates)).void
    )(
      _ => bot.execute(deleteWebhook()).void
    )

  private def createServer(): Resource[F, Server[F]] = {
    val dsl = Http4sDsl[F]
    import dsl._

    def app(): HttpApp[F] =
      HttpRoutes.of[F] {
        case req @ POST -> BotPath =>
          handleUpdateReq(req).flatMap {
            _.fold(Ok()) { m =>
              val methodReq = m.asInstanceOf[MethodReq[_]]
              val inputPartFiles = methodReq.files.collect {
                case (filename, InputPartFile(file)) => (filename, file)
              }
              val attachments = toFileDataParts(inputPartFiles, blocker)
              if (attachments.isEmpty)
                Ok(methodReq.json)
              else {
                val parts = toMultipartWithFormData(methodReq.json, inputPartFiles.keys.toList, attachments)
                Ok(parts).map(_.withHeaders(parts.headers))
              }
            }
          }
      }
        .orNotFound

    BlazeServerBuilder[F].bindHttp(port).withHttpApp(app()).resource
  }
}
