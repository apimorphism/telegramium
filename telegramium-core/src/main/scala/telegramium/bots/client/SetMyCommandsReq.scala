package telegramium.bots.client

import telegramium.bots.BotCommand

/** @param commands A JSON-serialized list of bot commands to be set as the
  * list of the bot's commands. At most 100 commands can be
  * specified. */
final case class SetMyCommandsReq(commands: List[BotCommand] = List.empty)
