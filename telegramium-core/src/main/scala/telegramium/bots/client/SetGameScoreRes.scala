package telegramium.bots.client

import telegramium.bots.Message

final case class SetGameScoreRes(ok: Boolean,
                                 description: Option[String] = Option.empty,
                                 result: Option[Message] = Option.empty)
