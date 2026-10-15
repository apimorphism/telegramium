package telegramium.bots.client

import telegramium.bots.BotCommandScope

/** @param scope
  *   A JSON-serialized object, describing scope of users. Defaults to <a
  *   href="https://core.telegram.org/bots/api#botcommandscopedefault">BotCommandScopeDefault</a>.
  * @param languageCode
  *   A two-letter ISO 639-1 language code or an empty string
  */
final case class GetMyCommandsReq(
  scope: Option[BotCommandScope] = Option.empty,
  languageCode: Option[String] = Option.empty
)
