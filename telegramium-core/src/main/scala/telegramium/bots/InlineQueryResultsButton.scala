package telegramium.bots

/** This object represents a button to be shown above inline query results. You <strong>must</strong> use exactly one of
  * the optional fields.
  *
  * @param text
  *   Label text on the button
  * @param webApp
  *   <em>Optional</em>. Description of the <a href="https://core.telegram.org/bots/webapps">Web App</a> that will be
  *   launched when the user presses the button. The Web App will be able to switch back to the inline mode using the
  *   method <a href="https://core.telegram.org/bots/webapps#initializing-mini-apps">switchInlineQuery</a> inside the
  *   Web App.
  * @param startParameter
  *   <em>Optional</em>. <a href="https://core.telegram.org/bots/features#deep-linking">Deep-linking</a> parameter for
  *   the /start message sent to the bot when a user presses the button. 1-64 characters, only <code>A-Z</code>,
  *   <code>a-z</code>, <code>0-9</code>, <code>_</code> and <code>-</code> are allowed.<br><br><em>Example:</em> An
  *   inline bot that sends YouTube videos can ask the user to connect the bot to their YouTube account to adapt search
  *   results accordingly. To do this, it displays a 'Connect your YouTube account' button above the results, or even
  *   before showing any. The user presses the button, switches to a private chat with the bot and, in doing so, passes
  *   a start parameter that instructs the bot to return an OAuth link. Once done, the bot can offer a <a
  *   href="https://core.telegram.org/bots/api#inlinekeyboardmarkup">switch_inline</a> button so that the user can
  *   easily return to the chat where they wanted to use the bot's inline capabilities.
  */
final case class InlineQueryResultsButton(
  text: String,
  webApp: Option[WebAppInfo] = Option.empty,
  startParameter: Option[String] = Option.empty
)
