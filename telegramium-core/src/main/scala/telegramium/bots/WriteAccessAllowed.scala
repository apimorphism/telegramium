package telegramium.bots

/** This object represents a service message about a user allowing a bot to write messages after adding the bot to the
  * attachment menu or launching a Web App from a link.
  *
  * @param webAppName
  *   Optional. Name of the Web App which was launched from a link
  */
final case class WriteAccessAllowed(webAppName: Option[String] = Option.empty)
