package telegramium.bots

/** This object represents a bot command.
  *
  * @param command
  *   Text of the command; 1-32 characters. Can contain only lowercase English letters, digits and underscores.
  * @param description
  *   Description of the command; 1-256 characters.
  */
final case class BotCommand(command: String, description: String)
