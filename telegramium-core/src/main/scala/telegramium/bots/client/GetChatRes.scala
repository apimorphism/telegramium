package telegramium.bots.client

import telegramium.bots.Chat

final case class GetChatRes(ok: Boolean,
                            description: Option[String] = Option.empty,
                            result: Option[Chat] = Option.empty)
