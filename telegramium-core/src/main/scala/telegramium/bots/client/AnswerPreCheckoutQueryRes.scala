package telegramium.bots.client

import telegramium.bots.Update

final case class AnswerPreCheckoutQueryRes(ok: Boolean,
                                           description: Option[String] = Option.empty,
                                           result: Option[Update] = Option.empty)
