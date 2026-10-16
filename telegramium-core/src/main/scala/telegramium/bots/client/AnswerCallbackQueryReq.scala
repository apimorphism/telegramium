package telegramium.bots.client

/** @param callbackQueryId
  *   Unique identifier for the query to be answered
  * @param text
  *   Text of the notification. If not specified, nothing will be shown to the user, 0-200 characters.
  * @param showAlert
  *   If <em>True</em>, an alert will be shown by the client instead of a notification at the top of the chat screen.
  *   Defaults to <em>False</em>.
  * @param url
  *   URL that will be opened by the user's client. If you have created a <a
  *   href="https://core.telegram.org/bots/api#game">Game</a> and accepted the conditions via <a
  *   href="https://t.me/botfather">&#064;BotFather</a>, specify the URL that opens your game - note that this will only
  *   work if the query comes from a <a href="https://core.telegram.org/bots/api#inlinekeyboardbutton">callback_game</a>
  *   button.<br>
  *
  * <br>
  *
  * Otherwise, you may use links like <code>t.me/your_bot?start=XXXX</code> that open your bot with a parameter.
  * @param cacheTime
  *   The maximum amount of time in seconds that the result of the callback query may be cached client-side. Defaults to
  *   0.
  */
final case class AnswerCallbackQueryReq(
  callbackQueryId: String,
  text: Option[String] = Option.empty,
  showAlert: Option[Boolean] = Option.empty,
  url: Option[String] = Option.empty,
  cacheTime: Option[Int] = Option.empty
)
