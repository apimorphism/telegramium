package telegramium.bots.client

import telegramium.bots.File

final case class UploadStickerFileRes(ok: Boolean,
                                      description: Option[String] = Option.empty,
                                      result: Option[File] = Option.empty)
