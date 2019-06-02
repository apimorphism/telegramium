package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.Update

final case class AnswerPreCheckoutQueryRes(ok: Boolean,
                                           description: Option[String] = Option.empty,
                                           result: Option[Update] = Option.empty)
