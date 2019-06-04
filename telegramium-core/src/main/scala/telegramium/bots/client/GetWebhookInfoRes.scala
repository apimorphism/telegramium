package telegramium.bots.client

import telegramium.bots.WebhookInfo

final case class GetWebhookInfoRes(ok: Boolean,
                                   description: Option[String] = Option.empty,
                                   result: Option[WebhookInfo] = Option.empty)
