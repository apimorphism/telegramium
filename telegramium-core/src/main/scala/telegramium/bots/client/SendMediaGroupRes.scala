package telegramium.bots.client

import telegramium.bots.Message

final case class SendMediaGroupRes(ok: Boolean,
                                   description: Option[String] = Option.empty,
                                   result: List[Message] = List.empty)
