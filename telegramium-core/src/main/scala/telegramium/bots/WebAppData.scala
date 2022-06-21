package telegramium.bots

/** Describes data sent from a Web App to the bot.
  *
  * @param data
  *   The data. Be aware that a bad client can send arbitrary data in this field.
  * @param buttonText
  *   Text of the web_app keyboard button from which the Web App was opened. Be aware that a bad client can send
  *   arbitrary data in this field.
  */
final case class WebAppData(data: String, buttonText: String)
