package telegramium.bots.client

import telegramium.bots.StickerSet

final case class GetStickerSetRes(ok: Boolean,
                                  description: Option[String] = Option.empty,
                                  result: Option[StickerSet] = Option.empty)
