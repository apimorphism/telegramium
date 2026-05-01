package telegramium.bots

/** This object contains information about the creation, token update, or owner update of a bot that is managed by the
  * current bot.
  *
  * @param user
  *   User that created the bot
  * @param bot
  *   Information about the bot. Token of the bot can be fetched using the method getManagedBotToken.
  */
final case class ManagedBotUpdated(user: User, bot: User)
