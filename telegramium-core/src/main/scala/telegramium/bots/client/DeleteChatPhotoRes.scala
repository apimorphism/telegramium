package telegramium.bots.client

final case class DeleteChatPhotoRes(ok: Boolean,
                                    description: Option[String] = Option.empty,
                                    result: Option[Boolean] = Option.empty)
