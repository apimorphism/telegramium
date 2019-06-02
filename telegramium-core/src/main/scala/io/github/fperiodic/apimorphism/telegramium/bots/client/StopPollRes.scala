package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.Poll

final case class StopPollRes(ok: Boolean,
                             description: Option[String] = Option.empty,
                             result: Option[Poll] = Option.empty)
