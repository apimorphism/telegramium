package telegramium.bots.client

import telegramium.bots.Update

final case class AnswerShippingQueryRes(ok: Boolean,
                                        description: Option[String] = Option.empty,
                                        result: Option[Update] = Option.empty)
