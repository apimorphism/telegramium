package telegramium.bots

/** This object represents a service message about a user allowing a bot to write messages after adding it to the
  * attachment menu, launching a Web App from a link, or accepting an explicit request from a Web App sent by the method
  * <a href="https://core.telegram.org/bots/webapps#initializing-mini-apps">requestWriteAccess</a>.
  *
  * @param fromRequest
  *   <em>Optional</em>. <em>True</em>, if the access was granted after the user accepted an explicit request from a Web
  *   App sent by the method <a
  *   href="https://core.telegram.org/bots/webapps#initializing-mini-apps">requestWriteAccess</a>
  * @param webAppName
  *   <em>Optional</em>. Name of the Web App, if the access was granted when the Web App was launched from a link
  * @param fromAttachmentMenu
  *   <em>Optional</em>. <em>True</em>, if the access was granted when the bot was added to the attachment or side menu
  */
final case class WriteAccessAllowed(
  fromRequest: Option[Boolean] = Option.empty,
  webAppName: Option[String] = Option.empty,
  fromAttachmentMenu: Option[Boolean] = Option.empty
)
