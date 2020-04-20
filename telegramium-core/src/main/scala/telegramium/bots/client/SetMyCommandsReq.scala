package telegramium.bots.client

import telegramium.bots.BotCommand

final case class SetMyCommandsReq(
    /** A JSON-serialized list of bot commands to be set as the
      * list of the bot's commands. At most 100 commands can be
      * specified.*/
    commands: List[BotCommand] = List.empty)
