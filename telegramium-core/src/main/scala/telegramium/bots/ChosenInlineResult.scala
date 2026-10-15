package telegramium.bots

/** Represents a <a href="https://core.telegram.org/bots/api#inlinequeryresult">result</a> of an inline query that was
  * chosen by the user and sent to their chat partner.
  *
  * @param resultId
  *   The unique identifier for the result that was chosen
  * @param from
  *   The user that chose the result
  * @param query
  *   The query that was used to obtain the result
  * @param location
  *   <em>Optional</em>. Sender location, only for bots that require user location
  * @param inlineMessageId
  *   <em>Optional</em>. Identifier of the sent inline message. Available only if there is an <a
  *   href="https://core.telegram.org/bots/api#inlinekeyboardmarkup">inline keyboard</a> attached to the message. Will
  *   be also received in <a href="https://core.telegram.org/bots/api#callbackquery">callback queries</a> and can be
  *   used to <a href="https://core.telegram.org/bots/api#updating-messages">edit</a> the message.
  */
final case class ChosenInlineResult(
  resultId: String,
  from: User,
  query: String,
  location: Option[Location] = Option.empty,
  inlineMessageId: Option[String] = Option.empty
)
