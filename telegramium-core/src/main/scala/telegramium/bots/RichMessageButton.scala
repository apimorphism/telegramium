package telegramium.bots

/** This object represents a button in a <a href="https://core.telegram.org/bots/api#richmessage">RichMessage</a>.
  * Exactly one of the fields other than <em>text</em> and <em>style</em> must be used to specify the type of the
  * button.
  *
  * @param text
  *   Text of the button. May contain only plain text, <a
  *   href="https://core.telegram.org/bots/api#richtextcustomemoji">RichTextCustomEmoji</a> and <a
  *   href="https://core.telegram.org/bots/api#richtextdatetime">RichTextDateTime</a> entities.
  * @param style
  *   <em>Optional</em>. Style of the button. Must be one of “danger”, “success”, “primary”, or “link” (the button is
  *   shown as a regular link without borders). Apps may use theme-specific colors for the button background and text
  *   based on the style. The style “link” is allowed only for callback buttons.
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
  *   the current chat's input field. May be empty, in which case only the bot's username will be inserted. Not
  *   supported in channels and for messages sent in channel direct messages chats and on behalf of a business account.
  * @param switchInlineQueryChosenChat
  *   <em>Optional</em>. If set, pressing the button will prompt the user to select one of their chats of the specified
  *   type, open that chat and insert the bot's username and the specified inline query in the input field. Not
  *   supported for messages sent in channel direct messages chats and on behalf of a business account.
  * @param copyText
  *   <em>Optional</em>. A button that copies the specified text to the clipboard
  * @param disabled
  *   <em>Optional</em>. If set, then the button is disabled and does nothing
  */
final case class RichMessageButton(
  text: iozhik.OpenEnum[RichText],
  style: Option[String] = Option.empty,
  url: Option[String] = Option.empty,
  callbackData: Option[String] = Option.empty,
  webApp: Option[WebAppInfo] = Option.empty,
  loginUrl: Option[LoginUrl] = Option.empty,
  switchInlineQuery: Option[String] = Option.empty,
  switchInlineQueryCurrentChat: Option[String] = Option.empty,
  switchInlineQueryChosenChat: Option[SwitchInlineQueryChosenChat] = Option.empty,
  copyText: Option[CopyTextButton] = Option.empty,
  disabled: Option[DisabledButton.type] = Option.empty
)
