package telegramium.bots

/** This object represents one button of an inline keyboard. You must use exactly one of the optional fields.
  *
  * @param text
  *   Label text on the button
  * @param url
  *   Optional. HTTP or tg:// URL to be opened when the button is pressed. Links tg://user?id=<user_id> can be used to
  *   mention a user by their identifier without using a username, if this is allowed by their privacy settings.
  * @param callbackData
  *   Optional. Data to be sent in a callback query to the bot when button is pressed, 1-64 bytes
  * @param webApp
  *   Optional. Description of the Web App that will be launched when the user presses the button. The Web App will be
  *   able to send an arbitrary message on behalf of the user using the method answerWebAppQuery. Available only in
  *   private chats between a user and the bot.
  * @param loginUrl
  *   Optional. An HTTPS URL used to automatically authorize the user. Can be used as a replacement for the Telegram
  *   Login Widget.
  * @param switchInlineQuery
  *   Optional. If set, pressing the button will prompt the user to select one of their chats, open that chat and insert
  *   the bot's username and the specified inline query in the input field. May be empty, in which case just the bot's
  *   username will be inserted.
  * @param switchInlineQueryCurrentChat
  *   Optional. If set, pressing the button will insert the bot's username and the specified inline query in the current
  *   chat's input field. May be empty, in which case only the bot's username will be inserted. This offers a quick way
  *   for the user to open your bot in inline mode in the same chat - good for selecting something from multiple
  *   options.
  * @param switchInlineQueryChosenChat
  *   Optional. If set, pressing the button will prompt the user to select one of their chats of the specified type,
  *   open that chat and insert the bot's username and the specified inline query in the input field
  * @param callbackGame
  *   Optional. Description of the game that will be launched when the user presses the button. NOTE: This type of
  *   button must always be the first button in the first row.
  * @param pay
  *   Optional. Specify True, to send a Pay button. NOTE: This type of button must always be the first button in the
  *   first row and can only be used in invoice messages.
  */
final case class InlineKeyboardButton(
  text: String,
  url: Option[String] = Option.empty,
  callbackData: Option[String] = Option.empty,
  webApp: Option[WebAppInfo] = Option.empty,
  loginUrl: Option[LoginUrl] = Option.empty,
  switchInlineQuery: Option[String] = Option.empty,
  switchInlineQueryCurrentChat: Option[String] = Option.empty,
  switchInlineQueryChosenChat: Option[SwitchInlineQueryChosenChat] = Option.empty,
  callbackGame: Option[CallbackGame.type] = Option.empty,
  pay: Option[Boolean] = Option.empty
)
