package io.github.fperiodic.apimorphism.telegramium.bots.client

final case class DeleteWebhookRes(ok: Boolean,
                                  description: Option[String] = Option.empty,
                                  result: Option[Boolean] = Option.empty)
