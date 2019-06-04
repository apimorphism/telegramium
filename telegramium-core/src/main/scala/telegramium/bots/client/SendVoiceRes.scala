package telegramium.bots.client

import telegramium.bots.Audio

final case class SendVoiceRes(ok: Boolean,
                              description: Option[String] = Option.empty,
                              result: Option[Audio] = Option.empty)
