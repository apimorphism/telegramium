package telegramium.bots.client

/** @param callbackQueryId
  *   Unique identifier for the query to be answered
  * @param text
  *   Text of the notification. If not specified, nothing will be shown to the user, 0-200 characters
  * @param showAlert
  *   If True, an alert will be shown by the client instead of a notification at the top of the chat screen. Defaults to
  *   false.
  * @param url
  *   URL that will be opened by the user's client. If you have created a Game and accepted the conditions via
  *   &#064;Botfather, specify the URL that opens your game — note that this will only work if the query comes from a
  *   callback_game button. Otherwise, you may use links like t.me/your_bot?start=XXXX that open your bot with a
  *   parameter.
  * @param cacheTime
  *   The maximum amount of time in seconds that the result of the callback query may be cached client-side. Telegram
  *   apps will support caching starting in version 3.14. Defaults to 0.
  */
final case class AnswerCallbackQueryReq(
  callbackQueryId: String,
  text: Option[String] = Option.empty,
  showAlert: Option[Boolean] = Option.empty,
  url: Option[String] = Option.empty,
  cacheTime: Option[Int] = Option.empty
)
