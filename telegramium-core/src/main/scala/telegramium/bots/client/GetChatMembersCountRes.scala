package telegramium.bots.client

final case class GetChatMembersCountRes(ok: Boolean,
                                        description: Option[String] = Option.empty,
                                        result: Option[Int] = Option.empty)
