package telegramium.bots.high

import java.util.concurrent.Executors

import cats.MonadError
import cats.effect.{Blocker, ConcurrentEffect, ContextShift}
import org.http4s.client.Client
import telegramium.bots.client.{Api, ApiHttp4sImp}

import scala.concurrent.ExecutionContext

object Http4sApi {

  private lazy val defaultBlocker: Blocker = Blocker.liftExecutionContext {
    ExecutionContext.fromExecutor(Executors.newCachedThreadPool(
      (r: Runnable) => {
        val thread = new Thread(r)
        thread.setName(s"telegramium-io-thread-${thread.getId}")
        thread.setDaemon(true)
        thread
      }
    ))
  }

  /**
   * @param blocker The `Blocker` to use for blocking IO operations. If not provided, a default `Blocker` will be used.
   */
  def create[F[_]: ConcurrentEffect: ContextShift](http: Client[F], baseUrl: String, blocker: Blocker = defaultBlocker)(
    implicit F: MonadError[F, Throwable]
  ): Api[F] = new ApiHttp4sImp[F](http, baseUrl, blocker)

}
