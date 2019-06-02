package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.Audio

final case class SendVoiceRes(ok: Boolean,
                              description: Option[String] = Option.empty,
                              result: Option[Audio] = Option.empty)
