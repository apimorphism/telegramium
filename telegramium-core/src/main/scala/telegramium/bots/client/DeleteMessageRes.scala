package telegramium.bots.client

final case class DeleteMessageRes(ok: Boolean,
                                  description: Option[String] = Option.empty,
                                  result: Option[Boolean] = Option.empty)
