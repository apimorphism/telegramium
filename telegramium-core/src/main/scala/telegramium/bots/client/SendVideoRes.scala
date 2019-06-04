package telegramium.bots.client

import telegramium.bots.Document

final case class SendVideoRes(ok: Boolean,
                              description: Option[String] = Option.empty,
                              result: Option[Document] = Option.empty)
