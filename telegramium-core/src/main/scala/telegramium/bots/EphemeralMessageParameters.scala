package telegramium.bots

/** @param receiverUserId
  *   Identifier of the user who will receive the message. It is not guaranteed that the user will receive the message,
  *   especially if they are offline. See here for more details.
  * @param callbackQueryId
  *   Optional. Identifier of the callback query which triggered the message, if any
  * @param replaceCallbackQueryMessage
  *   Optional. Pass True if the ephemeral message must be shown in place of the original message. Must be False for
  *   callback queries from ephemeral messages, which must be edited using regular editEphemeralMessage… methods.
  */
final case class EphemeralMessageParameters(
  receiverUserId: Long,
  callbackQueryId: Option[String] = Option.empty,
  replaceCallbackQueryMessage: Option[Boolean] = Option.empty
)
