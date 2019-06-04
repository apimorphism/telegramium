package telegramium.bots.client

import telegramium.bots.Update

final case class GetUpdatesRes(ok: Boolean,
                               description: Option[String] = Option.empty,
                               result: List[Update] = List.empty)
