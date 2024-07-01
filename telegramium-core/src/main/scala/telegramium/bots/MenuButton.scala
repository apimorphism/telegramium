package telegramium.bots

sealed trait MenuButton {}

/** Describes that no specific value for the menu button was set. */
case object MenuButtonDefault extends MenuButton

/** Represents a menu button, which launches a Web App.
  *
  * @param text
  *   Text on the button
  * @param webApp
  *   Description of the Web App that will be launched when the user presses the button. The Web App will be able to
  *   send an arbitrary message on behalf of the user using the method answerWebAppQuery. Alternatively, a t.me link to
  *   a Web App of the bot can be specified in the object instead of the Web App's URL, in which case the Web App will
  *   be opened as if the user pressed the link.
  */
final case class MenuButtonWebApp(text: String, webApp: WebAppInfo) extends MenuButton

/** Represents a menu button, which opens the bot's list of commands. */
case object MenuButtonCommands extends MenuButton
