package io.github.fperiodic.apimorphism.telegramium.bots.client

final case class ExportChatInviteLinkRes(ok: Boolean,
                                         description: Option[String] = Option.empty,
                                         result: Option[String] = Option.empty)
