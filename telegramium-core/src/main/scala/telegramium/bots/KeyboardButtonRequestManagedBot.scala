package telegramium.bots

/** This object defines the parameters for the creation of a managed bot. Information about the created bot will be
  * shared with the bot using the update managed_bot and a Message with the field managed_bot_created.
  *
  * @param requestId
  *   Signed 32-bit identifier of the request. Must be unique within the message
  * @param suggestedName
  *   Optional. Suggested name for the bot
  * @param suggestedUsername
  *   Optional. Suggested username for the bot
  */
final case class KeyboardButtonRequestManagedBot(
  requestId: Int,
  suggestedName: Option[String] = Option.empty,
  suggestedUsername: Option[String] = Option.empty
)
