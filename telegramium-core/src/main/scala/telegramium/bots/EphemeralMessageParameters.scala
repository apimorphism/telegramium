package telegramium.bots

/** @param receiverUserId
  *   Identifier of the user who will receive the message. It is not guaranteed that the user will receive the message,
  *   especially if they are offline. See <a
  *   href="https://core.telegram.org/bots/api#ephemeral-messages-and-commands">here</a> for more details.
  * @param callbackQueryId
  *   <em>Optional</em>. Identifier of the callback query which triggered the message, if any
  * @param replaceCallbackQueryMessage
  *   <em>Optional</em>. Pass <em>True</em> if the ephemeral message must be shown in place of the original message.
  *   Must be <em>False</em> for callback queries from ephemeral messages, which must be edited using regular
  *   <em>editEphemeralMessage…</em> methods.
  */
final case class EphemeralMessageParameters(
  receiverUserId: Long,
  callbackQueryId: Option[String] = Option.empty,
  replaceCallbackQueryMessage: Option[Boolean] = Option.empty
)
