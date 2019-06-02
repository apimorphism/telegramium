package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.User

final case class GetMeRes(ok: Boolean,
                          description: Option[String] = Option.empty,
                          result: Option[User] = Option.empty)
