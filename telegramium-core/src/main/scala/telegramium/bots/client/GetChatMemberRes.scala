package telegramium.bots.client

import telegramium.bots.ChatMember

final case class GetChatMemberRes(ok: Boolean,
                                  description: Option[String] = Option.empty,
                                  result: Option[ChatMember] = Option.empty)
