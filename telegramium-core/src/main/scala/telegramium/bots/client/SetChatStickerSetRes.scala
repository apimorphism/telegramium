package telegramium.bots.client

final case class SetChatStickerSetRes(ok: Boolean,
                                      description: Option[String] = Option.empty,
                                      result: Option[Boolean] = Option.empty)
