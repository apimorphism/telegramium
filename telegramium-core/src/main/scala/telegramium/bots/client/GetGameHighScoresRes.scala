package telegramium.bots.client

import telegramium.bots.GameHighScore

final case class GetGameHighScoresRes(ok: Boolean,
                                      description: Option[String] = Option.empty,
                                      result: List[GameHighScore] = List.empty)
