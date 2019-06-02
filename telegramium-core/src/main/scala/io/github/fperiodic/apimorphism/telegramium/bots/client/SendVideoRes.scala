package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.Document

final case class SendVideoRes(ok: Boolean,
                              description: Option[String] = Option.empty,
                              result: Option[Document] = Option.empty)
