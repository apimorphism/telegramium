package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.ChatMember

final case class GetChatAdministratorsRes(ok: Boolean,
                                          description: Option[String] = Option.empty,
                                          result: List[ChatMember] = List.empty)
