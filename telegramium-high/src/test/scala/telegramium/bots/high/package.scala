package telegramium.bots

import com.dimafeng.testcontainers.MockServerContainer
import org.testcontainers.containers.wait.strategy.Wait

package object high {
  val MockServerContainerVersion = "5.14.0"

  // At INFO MockServer logs every request against every expectation, tens of thousands of lines per
  // test run. With Docker's journald log driver that trips journald's rate limit, which then drops the
  // log line Testcontainers waits on to see Ryuk start. WARN also hides MockServer's own
  // "started on port" line, so readiness is checked over HTTP instead.
  def mockServerContainer(): MockServerContainer =
    MockServerContainer(MockServerContainerVersion).configure { c =>
      c.withEnv("MOCKSERVER_LOG_LEVEL", "WARN")
      c.waitingFor(Wait.forHttp("/mockserver/status").withMethod("PUT").forStatusCode(200))
      ()
    }
}
