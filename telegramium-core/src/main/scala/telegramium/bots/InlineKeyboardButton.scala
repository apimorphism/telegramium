package telegramium.bots

/** This object represents one button of an inline keyboard. Exactly one of the fields other than <em>text</em>,
  * <em>icon_custom_emoji_id</em>, and <em>style</em> must be used to specify the type of the button.
  *
  * @param text
  *   Label text on the button
  * @param iconCustomEmojiId
  *   <em>Optional</em>. Unique identifier of the custom emoji shown before the text of the button. Can only be used by
  *   bots that purchased additional usernames on <a href="https://fragment.com">Fragment</a> or in the messages
  *   directly sent by the bot to private, group and supergroup chats if the owner of the bot has a Telegram Premium
  *   subscription.
  * @param style
  *   <em>Optional</em>. Style of the button. Must be one of “danger” (red), “success” (green) or “primary” (blue). If
  *   omitted, then an app-specific style is used.
  * @param url
  *   <em>Optional</em>. HTTP or tg:// URL to be opened when the button is pressed. Links
  *   <code>tg://user?id=&lt;user_id&gt;</code> can be used to mention a user by their identifier without using a
  *   username, if this is allowed by their privacy settings.
  * @param callbackData
  *   <em>Optional</em>. Data to be sent in a <a href="https://core.telegram.org/bots/api#callbackquery">callback
  *   query</a> to the bot when the button is pressed, 1-64 bytes
  * @param webApp
  *   <em>Optional</em>. Description of the <a href="https://core.telegram.org/bots/webapps">Web App</a> that will be
  *   launched when the user presses the button. The Web App will be able to send an arbitrary message on behalf of the
  *   user using the method <a href="https://core.telegram.org/bots/api#answerwebappquery">answerWebAppQuery</a>.
  *   Available only in private chats between a user and the bot. Not supported for messages sent on behalf of a
  *   business account.
  * @param loginUrl
  *   <em>Optional</em>. An HTTPS URL used to automatically authorize the user. Can be used as a replacement for the <a
  *   href="https://core.telegram.org/widgets/login">Telegram Login Widget</a>. Not supported for ephemeral messages.
  * @param switchInlineQuery
  *   <em>Optional</em>. If set, pressing the button will prompt the user to select one of their chats, open that chat
  *   and insert the bot's username and the specified inline query in the input field. May be empty, in which case just
  *   the bot's username will be inserted. Not supported for messages sent in channel direct messages chats and on
  *   behalf of a business account.
  * @param switchInlineQueryCurrentChat
  *   <em>Optional</em>. If set, pressing the button will insert the bot's username and the specified inline query in
  *   the current chat's input field. May be empty, in which case only the bot's username will be inserted.<br>
  *
  * <br>
  *
  * This offers a quick way for the user to open your bot in inline mode in the same chat - good for selecting something
  * from multiple options. Not supported in channels and for messages sent in channel direct messages chats and on
  * behalf of a business account.
  * @param switchInlineQueryChosenChat
  *   <em>Optional</em>. If set, pressing the button will prompt the user to select one of their chats of the specified
  *   type, open that chat and insert the bot's username and the specified inline query in the input field. Not
  *   supported for messages sent in channel direct messages chats and on behalf of a business account.
  * @param copyText
  *   <em>Optional</em>. Description of the button that copies the specified text to the clipboard
  * @param callbackGame
  *   <em>Optional</em>. Description of the game that will be launched when the user presses the button.<br>
  *
  * <br>
  *
  * <strong>NOTE:</strong> This type of button <strong>must</strong> always be the first button in the first row.
  * @param pay
  *   <em>Optional</em>. Specify <em>True</em>, to send a <a href="https://core.telegram.org/bots/api#payments">Pay
  *   button</a>. Substrings “⭐” and “XTR” in the buttons's text will be replaced with a Telegram Star icon.<br>
  *
  * <br>
  *
  * <strong>NOTE:</strong> This type of button <strong>must</strong> always be the first button in the first row and can
  * only be used in invoice messages.
  * @param disabled
  *   <em>Optional</em>. If set, then the button is disabled and does nothing
  */
final case class InlineKeyboardButton(
  text: String,
  iconCustomEmojiId: Option[String] = Option.empty,
  style: Option[String] = Option.empty,
  url: Option[String] = Option.empty,
  callbackData: Option[String] = Option.empty,
  webApp: Option[WebAppInfo] = Option.empty,
  loginUrl: Option[LoginUrl] = Option.empty,
  switchInlineQuery: Option[String] = Option.empty,
  switchInlineQueryCurrentChat: Option[String] = Option.empty,
  switchInlineQueryChosenChat: Option[SwitchInlineQueryChosenChat] = Option.empty,
  copyText: Option[CopyTextButton] = Option.empty,
  callbackGame: Option[CallbackGame.type] = Option.empty,
  pay: Option[Boolean] = Option.empty,
  disabled: Option[DisabledButton.type] = Option.empty
)
