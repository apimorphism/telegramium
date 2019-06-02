package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.Chat

final case class GetChatRes(ok: Boolean,
                            description: Option[String] = Option.empty,
                            result: Option[Chat] = Option.empty)
