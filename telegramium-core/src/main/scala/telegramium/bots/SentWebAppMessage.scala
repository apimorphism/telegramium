package telegramium.bots

/** Contains information about an inline message sent by a Web App on behalf of a user.
  *
  * @param inlineMessageId
  *   Optional. Identifier of the sent inline message. Available only if there is an inline keyboard attached to the
  *   message.
  */
final case class SentWebAppMessage(inlineMessageId: Option[String] = Option.empty)
