package telegramium.bots

/** This object represents a bot command.
  *
  * @param command
  *   Text of the command; 1-32 characters. Can contain only lowercase English letters, digits and underscores.
  * @param description
  *   Description of the command; 1-256 characters
  * @param isEphemeral
  *   Optional. True, if the command sends an ephemeral message, which can be seen only by the sender of the message and
  *   the bot
  */
final case class BotCommand(command: String, description: String, isEphemeral: Option[Boolean] = Option.empty)
