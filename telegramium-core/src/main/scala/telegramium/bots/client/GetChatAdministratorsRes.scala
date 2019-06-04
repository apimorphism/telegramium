package telegramium.bots.client

import telegramium.bots.ChatMember

final case class GetChatAdministratorsRes(ok: Boolean,
                                          description: Option[String] = Option.empty,
                                          result: List[ChatMember] = List.empty)
