package telegramium.bots.examples

import monix.eval.Task
import monix.execution.Scheduler
import monix.execution.Scheduler.Implicits.global
import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.client.middleware.Logger
import telegramium.bots.high.{Api, BotApi}

import scala.concurrent.duration.Duration

object Application extends App {

  BlazeClientBuilder[Task](global).resource.use { httpClient =>
    val blocker = Blocker.liftExecutionContext(Scheduler.io())
    val http = Logger(logBody = true, logHeaders = true)(httpClient)
    args match {
      case Array("EchoBot", token) =>
        implicit val api: Api[Task] = createBotBackend(http, token, blocker)
        val echoBot = new EchoBot()
        echoBot.start()
      case Array("IceCreamParlorBot", token) =>
        implicit val api: Api[Task] = createBotBackend(http, token, blocker)
        val iceCreamParlorBot = new IceCreamParlorBot()
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
  private def createBotBackend(http: Client[Task], token: String) =
    BotApi(http, baseUrl = s"https://api.telegram.org/bot$token", blocker)

}
