package telegramium.bots.client

import telegramium.bots.BotCommandScope

/** @param scope
  *   A JSON-serialized object, describing scope of users for which the commands are relevant. Defaults to
  *   BotCommandScopeDefault.
  * @param languageCode
  *   A two-letter ISO 639-1 language code. If empty, commands will be applied to all users from the given scope, for
  *   whose language there are no dedicated commands
  */
final case class DeleteMyCommandsReq(
  scope: Option[BotCommandScope] = Option.empty,
  languageCode: Option[String] = Option.empty
)
