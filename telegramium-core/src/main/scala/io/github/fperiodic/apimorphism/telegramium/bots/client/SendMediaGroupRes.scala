package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.Message

final case class SendMediaGroupRes(ok: Boolean,
                                   description: Option[String] = Option.empty,
                                   result: List[Message] = List.empty)
