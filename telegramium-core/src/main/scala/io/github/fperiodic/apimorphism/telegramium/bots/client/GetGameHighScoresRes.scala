package io.github.fperiodic.apimorphism.telegramium.bots.client

import io.github.fperiodic.apimorphism.telegramium.bots.GameHighScore

final case class GetGameHighScoresRes(ok: Boolean,
                                      description: Option[String] = Option.empty,
                                      result: List[GameHighScore] = List.empty)
