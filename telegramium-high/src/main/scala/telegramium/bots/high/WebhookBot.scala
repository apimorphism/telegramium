package telegramium.bots.high

import cats.Monad
import cats.effect.{Async, Resource}
import cats.syntax.all.*
import org.http4s.Uri.Path
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.circe.{jsonEncoder, jsonOf}
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits.*
import org.http4s.server.Server
import org.http4s.{EntityDecoder, HttpRoutes}
import telegramium.bots.CirceImplicits.*
import telegramium.bots.client.{Method, Methods as ApiMethods}
import telegramium.bots.high.Http4sUtils.{toFileDataParts, toMultipartWithFormData}
import telegramium.bots.{CallbackQuery, ChatJoinRequest, ChatMemberUpdated, ChosenInlineResult, InlineQuery, InputPartFile, Message, Poll, PollAnswer, PreCheckoutQuery, ShippingQuery, Update}

/** @param url
  *   HTTPS url to send updates to. Use an empty string to remove webhook integration
  * @param path
  *   Webhook route. Must be consistent with `url`. ''If you'd like to make sure that the Webhook request comes from
  *   Telegram, we recommend using a secret path in the URL, e.g. https://www.example.com/&lt;token&gt;. Since nobody
  *   else knows your bot's token, you can be pretty sure it's us.''
  * @param certificate
  *   Upload your public key certificate so that the root certificate in use can be checked.
  * @param ipAddress
  *   The fixed IP address which will be used to send webhook requests instead of the IP address resolved through DNS.
  * @param maxConnections
  *   Maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery, 1-100. Defaults to
  *   40. Use lower values to limit the load on your bot‘s server, and higher values to increase your bot’s throughput.
  * @param allowedUpdates
  *   A list of the update types you want your bot to receive. For example, specify [“message”, “edited_channel_post”,
  *   “callback_query”] to only receive updates of these types. See Update for a complete list of available update
  *   types. Specify an empty list to receive all updates regardless of type (default). If not specified, the previous
  *   setting will be used. Please note that this parameter doesn't affect updates created before the call to the
  *   setWebhook, so unwanted updates may be received for a short period of time.
  */
abstract class WebhookBot[F[_]: Async](
  bot: Api[F],
  url: String,
  path: String = "/",
  certificate: Option[InputPartFile] = Option.empty,
  ipAddress: Option[String] = Option.empty,
  maxConnections: Option[Int] = Option.empty,
  allowedUpdates: List[String] = List.empty
) extends ApiMethods {

  private val BotPath = Path.unsafeFromString(if (path.startsWith("/")) path else "/" + path)

  private def noop[A](a: A) = Monad[F].pure(a).void

  def onMessage(msg: Message): F[Unit]                                = noop(msg)
  def onEditedMessage(msg: Message): F[Unit]                          = noop(msg)
  def onChannelPost(msg: Message): F[Unit]                            = noop(msg)
  def onEditedChannelPost(msg: Message): F[Unit]                      = noop(msg)
  def onInlineQuery(query: InlineQuery): F[Unit]                      = noop(query)
  def onCallbackQuery(query: CallbackQuery): F[Unit]                  = noop(query)
  def onChosenInlineResult(inlineResult: ChosenInlineResult): F[Unit] = noop(inlineResult)
  def onShippingQuery(query: ShippingQuery): F[Unit]                  = noop(query)
  def onPreCheckoutQuery(query: PreCheckoutQuery): F[Unit]            = noop(query)
  def onPoll(poll: Poll): F[Unit]                                     = noop(poll)
  def onPollAnswer(pollAnswer: PollAnswer): F[Unit]                   = noop(pollAnswer)
  def onMyChatMember(myChatMember: ChatMemberUpdated): F[Unit]        = noop(myChatMember)
  def onChatMember(chatMember: ChatMemberUpdated): F[Unit]            = noop(chatMember)
  def onChatJoinRequest(request: ChatJoinRequest): F[Unit]            = noop(request)

  private def noopReply[A](a: A) = Monad[F].pure(a).map(_ => Option.empty[Method[?]])

  def onMessageReply(msg: Message): F[Option[Method[?]]]               = noopReply(msg)
  def onEditedMessageReply(msg: Message): F[Option[Method[?]]]         = noopReply(msg)
  def onChannelPostReply(msg: Message): F[Option[Method[?]]]           = noopReply(msg)
  def onEditedChannelPostReply(msg: Message): F[Option[Method[?]]]     = noopReply(msg)
  def onInlineQueryReply(query: InlineQuery): F[Option[Method[?]]]     = noopReply(query)
  def onCallbackQueryReply(query: CallbackQuery): F[Option[Method[?]]] = noopReply(query)

  def onChosenInlineResultReply(inlineResult: ChosenInlineResult): F[Option[Method[?]]] =
    Monad[F].pure(inlineResult).map(_ => Option.empty[Method[?]])

  def onShippingQueryReply(query: ShippingQuery): F[Option[Method[?]]]           = noopReply(query)
  def onPreCheckoutQueryReply(query: PreCheckoutQuery): F[Option[Method[?]]]     = noopReply(query)
  def onPollReply(poll: Poll): F[Option[Method[?]]]                              = noopReply(poll)
  def onPollAnswerReply(pollAnswer: PollAnswer): F[Option[Method[?]]]            = noopReply(pollAnswer)
  def onMyChatMemberReply(myChatMember: ChatMemberUpdated): F[Option[Method[?]]] = noopReply(myChatMember)
  def onChatMemberReply(chatMember: ChatMemberUpdated): F[Option[Method[?]]]     = noopReply(chatMember)
  def onChatJoinRequestReply(request: ChatJoinRequest): F[Option[Method[?]]]     = noopReply(request)

  def onUpdate(update: Update): F[Option[Method[?]]] =
    List(
      update.message.map(msg => onMessageReply(msg) <* onMessage(msg)),
      update.editedMessage.map(msg => onEditedMessageReply(msg) <* onEditedMessage(msg)),
      update.channelPost.map(msg => onChannelPostReply(msg) <* onChannelPost(msg)),
      update.editedChannelPost.map(msg => onEditedChannelPostReply(msg) <* onEditedChannelPost(msg)),
      update.inlineQuery.map(query => onInlineQueryReply(query) <* onInlineQuery(query)),
      update.callbackQuery.map(query => onCallbackQueryReply(query) <* onCallbackQuery(query)),
      update.chosenInlineResult.map(inlineResult =>
        onChosenInlineResultReply(inlineResult) <* onChosenInlineResult(inlineResult)
      ),
      update.shippingQuery.map(query => onShippingQueryReply(query) <* onShippingQuery(query)),
      update.preCheckoutQuery.map(query => onPreCheckoutQueryReply(query) <* onPreCheckoutQuery(query)),
      update.poll.map(poll => onPollReply(poll) <* onPoll(poll)),
      update.pollAnswer.map(pollAnswer => onPollAnswerReply(pollAnswer) <* onPollAnswer(pollAnswer)),
      update.myChatMember.map(myChatMember => onMyChatMemberReply(myChatMember) <* onMyChatMember(myChatMember)),
      update.chatMember.map(chatMember => onChatMemberReply(chatMember) <* onChatMember(chatMember)),
      update.chatJoinRequest.map(request => onChatJoinRequestReply(request) <* onChatJoinRequest(request))
    ).flatten.head

  private implicit val HandleUpdateReqEntityDecoder: EntityDecoder[F, Update] = jsonOf[F, Update]

  private def handleUpdateReq(rawReq: org.http4s.Request[F]): F[Option[Method[?]]] = rawReq.as[Update].flatMap(onUpdate)

  /** @param port
    *   port used to bind the resulting Server
    * @param host
    *   host used to bind the resulting Server
    */
  def start(
    port: Int,
    host: String = org.http4s.server.defaults.IPv4Host
  ): Resource[F, Server] =
    createServer(port, host) <* setWebhookResource()

  private def setWebhookResource(): Resource[F, Unit] =
    Resource.eval(setWebhook(url, certificate, ipAddress, maxConnections, allowedUpdates))

  private def setWebhook(
    url: String,
    certificate: Option[InputPartFile],
    ipAddress: Option[String],
    maxConnections: Option[Int],
    allowedUpdates: List[String]
  ): F[Unit] =
    bot.execute(setWebhook(url, certificate, ipAddress, maxConnections, allowedUpdates)).void

  private def routes(): HttpRoutes[F] = {
    val dsl = Http4sDsl[F]
    import dsl.*

    HttpRoutes.of[F] { case req @ POST -> BotPath =>
      handleUpdateReq(req).flatMap {
        _.fold(Ok()) { m =>
          val inputPartFiles = m.payload.files.collect { case (filename, InputPartFile(file)) =>
            (filename, file)
          }
          val attachments = toFileDataParts(inputPartFiles)
          if (attachments.isEmpty)
            Ok(m.payload.json)
          else {
            val parts = toMultipartWithFormData(m.payload.json, inputPartFiles.keys.toList, attachments)
            Ok(parts).map(_.withHeaders(parts.headers))
          }
        }
      }
    }
  }

  private def createServer(port: Int, host: String): Resource[F, Server] =
    BlazeServerBuilder[F].bindHttp(port, host).withHttpApp(routes().orNotFound).resource

}

object WebhookBot {

  /** Use this method to compose multiple Webhook bots as a single Http Server that will register the webhooks and
    * handle the requests.
    *
    * @param bots
    *   List of bots to compose
    * @param port
    *   Port to bind to
    * @param host
    *   Host to bind to. Default localhost
    * @return
    *   `Resource[F, Server[F]]` Result Http server wrapped into a Resource data type
    */
  def compose[F[_]: Async](
    bots: List[WebhookBot[F]],
    port: Int,
    host: String = org.http4s.server.defaults.IPv4Host
  ): Resource[F, Server] = {

    val setWebhooksResource: Resource[F, Unit] = bots.foldMapM(_.setWebhookResource())
    val httpRoutes: HttpRoutes[F]              = bots.foldMapK(_.routes())

    val serverResource: Resource[F, Server] =
      BlazeServerBuilder[F]
        .bindHttp(port, host)
        .withHttpApp(httpRoutes.orNotFound)
        .resource

    serverResource <* setWebhooksResource
  }

}
