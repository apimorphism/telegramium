package telegramium.bots.client

final case class RestrictChatMemberRes(ok: Boolean,
                                       description: Option[String] = Option.empty,
                                       result: Option[Boolean] = Option.empty)
