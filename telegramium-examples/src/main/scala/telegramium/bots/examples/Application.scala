package telegramium.bots.examples

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.client.Client
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.client.middleware.Logger
import telegramium.bots.high.{Api, BotApi}

object Application extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    BlazeClientBuilder[IO].resource
      .use { httpClient =>
        val http = Logger(logBody = true, logHeaders = true)(httpClient)
        args match {
          case List("EchoBot", token) =>
            implicit val api: Api[IO] = createBotBackend(http, token)
            val echoBot               = new EchoBot()
            echoBot.start().as(ExitCode.Success)
          case List("IceCreamParlorBot", token) =>
            implicit val api: Api[IO] = createBotBackend(http, token)
            val iceCreamParlorBot     = new IceCreamParlorBot()
            iceCreamParlorBot.start().as(ExitCode.Success)
          case List(name, _) =>
            IO.raiseError(new RuntimeException(s"Unknown bot $name"))
          case _ =>
            IO.raiseError(new RuntimeException("Usage:\nApplication $botName $token"))
        }
      }

  /** @param token
    *   Bot API token got from Botfather
    */
  private def createBotBackend(http: Client[IO], token: String) =
    BotApi(http, baseUrl = s"https://api.telegram.org/bot$token")

}
