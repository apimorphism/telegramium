package telegramium.bots

/** Describes a <a href="https://core.telegram.org/bots/webapps">Web App</a>.
  *
  * @param url
  *   An HTTPS URL of a Web App to be opened with additional data as specified in <a
  *   href="https://core.telegram.org/bots/webapps#initializing-mini-apps">Initializing Web Apps</a>
  */
final case class WebAppInfo(url: String)
