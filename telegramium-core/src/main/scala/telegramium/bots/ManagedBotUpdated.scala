package telegramium.bots

/** This object contains information about the creation, token update, or owner update of a bot that is managed by the
  * current bot.
  *
  * @param user
  *   User that created the bot
  * @param bot
  *   Information about the bot. Token of the bot can be fetched using the method <a
  *   href="https://core.telegram.org/bots/api#getmanagedbottoken">getManagedBotToken</a>.
  */
final case class ManagedBotUpdated(user: User, bot: User)
