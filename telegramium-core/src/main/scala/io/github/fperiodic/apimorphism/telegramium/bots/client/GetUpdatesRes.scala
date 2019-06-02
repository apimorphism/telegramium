package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.Update

final case class GetUpdatesRes(ok: Boolean,
                               description: Option[String] = Option.empty,
                               result: List[Update] = List.empty)
