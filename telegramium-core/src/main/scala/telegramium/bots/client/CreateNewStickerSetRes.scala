package telegramium.bots.client

final case class CreateNewStickerSetRes(ok: Boolean,
                                        description: Option[String] = Option.empty,
                                        result: Option[Boolean] = Option.empty)
