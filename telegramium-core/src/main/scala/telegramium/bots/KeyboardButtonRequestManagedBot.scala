package telegramium.bots

/** This object defines the parameters for the creation of a managed bot. Information about the created bot will be
  * shared with the bot using the update <em>managed_bot</em> and a <a
  * href="https://core.telegram.org/bots/api#message">Message</a> with the field <em>managed_bot_created</em>.
  *
  * @param requestId
  *   Signed 32-bit identifier of the request. Must be unique within the message.
  * @param suggestedName
  *   <em>Optional</em>. Suggested name for the bot
  * @param suggestedUsername
  *   <em>Optional</em>. Suggested username for the bot. The username must end with “bot”. If the user creating the bot
  *   has a Telegram Premium subscription, then the username can also end with “agent”, “ai”, “app”, “game”, or “gay”;
  *   in this case, the username will be assigned to the bot as a secondary username, and a regular username ending with
  *   “bot” will be assigned to the bot automatically.
  */
final case class KeyboardButtonRequestManagedBot(
  requestId: Int,
  suggestedName: Option[String] = Option.empty,
  suggestedUsername: Option[String] = Option.empty
)
