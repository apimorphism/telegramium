package telegramium.bots.client

import telegramium.bots.BotCommand
import telegramium.bots.BotCommandScope

/** @param commands
  *   A JSON-serialized list of bot commands to be set as the list of the bot's commands. At most 100 commands can be
  *   specified.
  * @param scope
  *   A JSON-serialized object, describing scope of users for which the commands are relevant. Defaults to
  *   BotCommandScopeDefault.
  * @param languageCode
  *   A two-letter ISO 639-1 language code. If empty, commands will be applied to all users from the given scope, for
  *   whose language there are no dedicated commands.
  */
final case class SetMyCommandsReq(
  commands: List[BotCommand] = List.empty,
  scope: Option[BotCommandScope] = Option.empty,
  languageCode: Option[String] = Option.empty
)
