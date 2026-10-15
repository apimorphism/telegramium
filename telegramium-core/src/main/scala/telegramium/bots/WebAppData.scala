package telegramium.bots

/** Describes data sent from a <a href="https://core.telegram.org/bots/webapps">Web App</a> to the bot.
  *
  * @param data
  *   The data. Be aware that a bad client can send arbitrary data in this field.
  * @param buttonText
  *   Text of the <em>web_app</em> keyboard button from which the Web App was opened. Be aware that a bad client can
  *   send arbitrary data in this field.
  */
final case class WebAppData(data: String, buttonText: String)
