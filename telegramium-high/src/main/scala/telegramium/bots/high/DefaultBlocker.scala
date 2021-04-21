package telegramium.bots.high

import java.util.concurrent.Executors


import scala.concurrent.ExecutionContext

object DefaultBlocker {
  lazy val blocker: Blocker = Blocker.liftExecutionContext {
    ExecutionContext.fromExecutor(Executors.newCachedThreadPool(
      (r: Runnable) => {
        val thread = new Thread(r)
        thread.setName(s"telegramium-io-thread-${thread.getId}")
        thread.setDaemon(true)
        thread
      }
    ))
  }
}
