package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.StickerSet

final case class GetStickerSetRes(ok: Boolean,
                                  description: Option[String] = Option.empty,
                                  result: Option[StickerSet] = Option.empty)
