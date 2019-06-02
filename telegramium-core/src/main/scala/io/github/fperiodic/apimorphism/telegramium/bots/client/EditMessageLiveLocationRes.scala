package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.Message

final case class EditMessageLiveLocationRes(ok: Boolean,
                                            description: Option[String] = Option.empty,
                                            result: Option[Message] = Option.empty)
