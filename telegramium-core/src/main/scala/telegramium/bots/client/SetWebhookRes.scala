package telegramium.bots.client

import telegramium.bots.Update

final case class SetWebhookRes(ok: Boolean,
                               description: Option[String] = Option.empty,
                               result: Option[Update] = Option.empty)
