package telegramium.bots.client

import telegramium.bots.Message

final case class EditMessageTextRes(ok: Boolean,
                                    description: Option[String] = Option.empty,
                                    result: Option[Message] = Option.empty)
