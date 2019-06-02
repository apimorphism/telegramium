package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.File

final case class UploadStickerFileRes(ok: Boolean,
                                      description: Option[String] = Option.empty,
                                      result: Option[File] = Option.empty)
