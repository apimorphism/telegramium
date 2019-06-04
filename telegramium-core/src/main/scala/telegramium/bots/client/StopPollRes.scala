package telegramium.bots.client

import telegramium.bots.Poll

final case class StopPollRes(ok: Boolean,
                             description: Option[String] = Option.empty,
                             result: Option[Poll] = Option.empty)
