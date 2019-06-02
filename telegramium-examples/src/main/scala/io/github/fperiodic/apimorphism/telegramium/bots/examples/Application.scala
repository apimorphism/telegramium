package io.github.fperiodic.apimorphism.telegramium.bots.examples

import io.github.fperiodic.apimorphism.telegramium.bots.client.ApiHttp4sImp

import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

import org.http4s.client.middleware.Logger
import org.http4s.client.blaze.BlazeClientBuilder

import scala.concurrent.duration.Duration

object Application extends App {

  val baseUrl = "https://api.telegram.org/PLACE_YOUR_BOT_TOKEN_HERE"

  BlazeClientBuilder[Task](global).resource.use { httpClient =>
    val http = Logger(logBody = true, logHeaders = true)(httpClient)
    val bot = new ApiHttp4sImp(http, baseUrl = baseUrl)
    val echoBot = new EchoBot(bot)
    echoBot.start()
  }.runSyncUnsafe(Duration.Inf)

}
