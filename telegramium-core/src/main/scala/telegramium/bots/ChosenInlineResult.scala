package telegramium.bots

/** Represents a result of an inline query that was chosen by the user and sent to their chat partner.
  *
  * @param resultId
  *   The unique identifier for the result that was chosen
  * @param from
  *   The user that chose the result
  * @param query
  *   The query that was used to obtain the result
  * @param location
  *   Optional. Sender location, only for bots that require user location
  * @param inlineMessageId
  *   Optional. Identifier of the sent inline message. Available only if there is an inline keyboard attached to the
  *   message. Will be also received in callback queries and can be used to edit the message.
  */
final case class ChosenInlineResult(
  resultId: String,
  from: User,
  query: String,
  location: Option[Location] = Option.empty,
  inlineMessageId: Option[String] = Option.empty
)
