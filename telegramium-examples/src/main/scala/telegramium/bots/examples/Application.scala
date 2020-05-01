package telegramium.bots.examples

import cats.effect.Blocker
import monix.eval.Task
import monix.execution.Scheduler
import monix.execution.Scheduler.Implicits.global
import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.client.middleware.Logger
import telegramium.bots.client.ApiHttp4sImp

import scala.concurrent.duration.Duration

object Application extends App {

  BlazeClientBuilder[Task](global).resource.use { httpClient =>
    val blocker = Blocker.liftExecutionContext(Scheduler.io())
    val http = Logger(logBody = true, logHeaders = true)(httpClient)
    args match {
      case Array("EchoBot", token) =>
        val echoBot = new EchoBot(createBotBackend(http, token, blocker))
        echoBot.start()
      case Array("IceCreamParlorBot", token) =>
        val iceCreamParlorBot = new IceCreamParlorBot(createBotBackend(http, token, blocker))
        iceCreamParlorBot.start()
      case Array(name, _) =>
        Task.raiseError(new RuntimeException(s"Unknown bot $name"))
      case _ =>
        Task.raiseError(new RuntimeException("Usage:\nApplication $botName $token"))
    }
  }.runSyncUnsafe(Duration.Inf)

  /**
   * @param token Bot API token got from Botfather
   */
  private def createBotBackend(http: Client[Task], token: String, blocker: Blocker) =
    new ApiHttp4sImp(http, baseUrl = s"https://api.telegram.org/bot$token", blocker)

}
