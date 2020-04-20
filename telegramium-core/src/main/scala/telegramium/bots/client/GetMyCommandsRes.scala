package telegramium.bots.client

import telegramium.bots.BotCommand

final case class GetMyCommandsRes(ok: Boolean,
                                  description: Option[String] = Option.empty,
                                  result: List[BotCommand] = List.empty)
