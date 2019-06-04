package telegramium.bots.client

import telegramium.bots.Message

final case class StopMessageLiveLocationRes(ok: Boolean,
                                            description: Option[String] = Option.empty,
                                            result: Option[Message] = Option.empty)
