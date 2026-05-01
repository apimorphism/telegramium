package telegramium.bots

/** This object contains information about the bot that was created to be managed by the current bot.
  *
  * @param bot
  *   Information about the bot. The bot's token can be fetched using the method getManagedBotToken.
  */
final case class ManagedBotCreated(bot: User)
