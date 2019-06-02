package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.WebhookInfo

final case class GetWebhookInfoRes(ok: Boolean,
                                   description: Option[String] = Option.empty,
                                   result: Option[WebhookInfo] = Option.empty)
