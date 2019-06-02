package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.ChatMember

final case class GetChatMemberRes(ok: Boolean,
                                  description: Option[String] = Option.empty,
                                  result: Option[ChatMember] = Option.empty)
