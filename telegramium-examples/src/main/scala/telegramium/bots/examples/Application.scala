package telegramium.bots.examples

import telegramium.bots.client.ApiHttp4sImp

import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

import org.http4s.client.middleware.Logger
import org.http4s.client.blaze.BlazeClientBuilder

import scala.concurrent.duration.Duration

object Application extends App {

  val botApiToken = "BOT_API_TOKEN_GOT_FROM_BOTFATHER"
  val baseUrl = s"https://api.telegram.org/bot$botApiToken"

  BlazeClientBuilder[Task](global).resource.use { httpClient =>
    val http = Logger(logBody = true, logHeaders = true)(httpClient)
    val bot = new ApiHttp4sImp(http, baseUrl = baseUrl)
    val echoBot = new EchoBot(bot)
    echoBot.start()
  }.runSyncUnsafe(Duration.Inf)

}
