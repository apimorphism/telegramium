package telegramium.bots.client

final case class AnswerInlineQueryRes(ok: Boolean,
                                      description: Option[String] = Option.empty,
                                      result: Option[Boolean] = Option.empty)
