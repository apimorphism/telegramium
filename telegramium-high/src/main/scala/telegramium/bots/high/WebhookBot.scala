package telegramium.bots.high

import cats.effect.{Blocker, ConcurrentEffect, ContextShift, Resource, Sync, Timer}
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
import telegramium.bots.client.{Method, Methods}
import telegramium.bots.high.Http4sUtils.{toFileDataParts, toMultipartWithFormData}
import telegramium.bots.{CallbackQuery, ChosenInlineResult, InlineQuery, InputPartFile, Message, Poll, PollAnswer, PreCheckoutQuery, ShippingQuery, Update}

import scala.concurrent.ExecutionContext

/**
 * @param url            HTTPS url to send updates to. Use an empty string to remove webhook integration
 * @param path           Webhook route. Must be consistent with `url`.
 *                       ''If you'd like to make sure that the Webhook request comes from Telegram, we recommend using
 *                       a secret path in the URL, e.g. https://www.example.com/&lt;token&gt;. Since nobody else knows your
 *                       bot's token, you can be pretty sure it's us.''
 * @param certificate    Upload your public key certificate so that the root certificate in use can be checked.
 * @param ipAddress      The fixed IP address which will be used to send webhook requests instead of the IP address
 *                       resolved through DNS.
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
  ipAddress: Option[String] = Option.empty,
  maxConnections: Option[Int] = Option.empty,
  allowedUpdates: List[String] = List.empty,
  host: String = org.http4s.server.defaults.Host
)(implicit syncF: Sync[F], timer: Timer[F]) extends Methods {

  private val BotPath = Path(if (path.startsWith("/")) path else "/" + path)

  private def noop[A](a: A) = syncF.pure(a).void

  def onMessage(msg: Message): F[Unit] = noop(msg)
  def onEditedMessage(msg: Message): F[Unit] = noop(msg)
  def onChannelPost(msg: Message): F[Unit] = noop(msg)
  def onEditedChannelPost(msg: Message): F[Unit] = noop(msg)
  def onInlineQuery(query: InlineQuery): F[Unit] = noop(query)
  def onCallbackQuery(query: CallbackQuery): F[Unit] = noop(query)
  def onChosenInlineResult(inlineResult: ChosenInlineResult): F[Unit] = noop(inlineResult)
  def onShippingQuery(query: ShippingQuery): F[Unit] = noop(query)
  def onPreCheckoutQuery(query: PreCheckoutQuery): F[Unit] = noop(query)
  def onPoll(poll: Poll): F[Unit] = noop(poll)
  def onPollAnswer(pollAnswer: PollAnswer): F[Unit] = noop(pollAnswer)

  private def noopReply[A](a: A) = syncF.pure(a).map(_ => Option.empty[Method[_]])

  def onMessageReply(msg: Message): F[Option[Method[_]]] = noopReply(msg)
  def onEditedMessageReply(msg: Message): F[Option[Method[_]]] = noopReply(msg)
  def onChannelPostReply(msg: Message): F[Option[Method[_]]] = noopReply(msg)
  def onEditedChannelPostReply(msg: Message): F[Option[Method[_]]] = noopReply(msg)
  def onInlineQueryReply(query: InlineQuery): F[Option[Method[_]]] = noopReply(query)
  def onCallbackQueryReply(query: CallbackQuery): F[Option[Method[_]]] = noopReply(query)
  def onChosenInlineResultReply(inlineResult: ChosenInlineResult): F[Option[Method[_]]] =
    syncF.pure(inlineResult).map(_ => Option.empty[Method[_]])
  def onShippingQueryReply(query: ShippingQuery): F[Option[Method[_]]] = noopReply(query)
  def onPreCheckoutQueryReply(query: PreCheckoutQuery): F[Option[Method[_]]] = noopReply(query)
  def onPollReply(poll: Poll): F[Option[Method[_]]] = noopReply(poll)
  def onPollAnswerReply(pollAnswer: PollAnswer): F[Option[Method[_]]] = noopReply(pollAnswer)

  def onUpdate(update: Update): F[Option[Method[_]]] =
    List(
      update.message.map(msg => onMessageReply(msg) <* onMessage(msg)),
      update.editedMessage.map(msg => onEditedMessageReply(msg) <* onEditedMessage(msg)),
      update.channelPost.map(msg => onChannelPostReply(msg) <* onChannelPost(msg)),
      update.editedChannelPost.map(msg => onEditedChannelPostReply(msg) <* onEditedChannelPost(msg)),
      update.inlineQuery.map(query => onInlineQueryReply(query) <* onInlineQuery(query)),
      update.callbackQuery.map(query => onCallbackQueryReply(query) <* onCallbackQuery(query)),
      update.chosenInlineResult.map(inlineResult => onChosenInlineResultReply(inlineResult) <* onChosenInlineResult(inlineResult)),
      update.shippingQuery.map(query => onShippingQueryReply(query) <* onShippingQuery(query)),
      update.preCheckoutQuery.map(query => onPreCheckoutQueryReply(query) <* onPreCheckoutQuery(query)),
      update.poll.map(poll => onPollReply(poll) <* onPoll(poll)),
      update.pollAnswer.map(pollAnswer => onPollAnswerReply(pollAnswer) <* onPollAnswer(pollAnswer))
    )
      .flatten
      .head

  private implicit val HandleUpdateReqEntityDecoder: EntityDecoder[F, Update] = jsonOf[F, Update]

  private def handleUpdateReq(rawReq: org.http4s.Request[F]): F[Option[Method[_]]] = rawReq.as[Update].flatMap(onUpdate)

  /**
   * @param executionContext Execution Context the underlying blaze futures will be executed upon.
   */
  def start(executionContext: ExecutionContext = ExecutionContext.global): Resource[F, Server[F]] =
    setWebhook(url, certificate, ipAddress, maxConnections, allowedUpdates).flatMap(_ => createServer(executionContext))

  private def setWebhook(
    url: String,
    certificate: Option[InputPartFile],
    ipAddress: Option[String],
    maxConnections: Option[Int],
    allowedUpdates: List[String]
  ): Resource[F, Unit] =
    Resource.make(
      bot.execute(setWebhook(url, certificate, ipAddress, maxConnections, allowedUpdates)).void
    )(
      _ => bot.execute(deleteWebhook()).void
    )

  private def createServer(executionContext: ExecutionContext): Resource[F, Server[F]] = {
    val dsl = Http4sDsl[F]
    import dsl._

    def app(): HttpApp[F] =
      HttpRoutes.of[F] {
        case req @ POST -> BotPath =>
          handleUpdateReq(req).flatMap {
            _.fold(Ok()) { m =>
              val inputPartFiles = m.payload.files.collect {
                case (filename, InputPartFile(file)) => (filename, file)
              }
              val attachments = toFileDataParts(inputPartFiles, blocker)
              if (attachments.isEmpty)
                Ok(m.payload.json)
              else {
                val parts = toMultipartWithFormData(m.payload.json, inputPartFiles.keys.toList, attachments)
                Ok(parts).map(_.withHeaders(parts.headers))
              }
            }
          }
      }
        .orNotFound

    BlazeServerBuilder[F](executionContext).bindHttp(port, host).withHttpApp(app()).resource
  }
}
