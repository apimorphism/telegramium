package telegramium.bots.high

import org.scalatest.Tag

/** Tags a test that talks to real Telegram. Plain `sbt test` skips these; `sbt liveTest` runs only them. */
object LiveTest extends Tag("telegramium.bots.high.LiveTest")
