package telegramium.bots

/** This object describes the access settings of a bot.
  *
  * @param isAccessRestricted
  *   True, if only selected users can access the bot. The bot's owner can always access it.
  * @param addedUsers
  *   Optional. The list of other users who have access to the bot if the access is restricted
  */
final case class BotAccessSettings(isAccessRestricted: Boolean, addedUsers: List[User] = List.empty)
