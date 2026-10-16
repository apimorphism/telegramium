package telegramium.bots

/** Describes an inline message sent by a <a href="https://core.telegram.org/bots/webapps">Web App</a> on behalf of a
  * user.
  *
  * @param inlineMessageId
  *   <em>Optional</em>. Identifier of the sent inline message. Available only if there is an <a
  *   href="https://core.telegram.org/bots/api#inlinekeyboardmarkup">inline keyboard</a> attached to the message.
  */
final case class SentWebAppMessage(inlineMessageId: Option[String] = Option.empty)
