package telegramium.bots.client

import telegramium.bots.User

final case class GetMeRes(ok: Boolean,
                          description: Option[String] = Option.empty,
                          result: Option[User] = Option.empty)
