package telegramium.bots

/** This object represents one button of the reply keyboard. At most one of the fields other than <em>text</em>,
  * <em>icon_custom_emoji_id</em>, and <em>style</em> must be used to specify the type of the button. For simple text
  * buttons, <em>String</em> can be used instead of this object to specify the button text.
  *
  * @param text
  *   Text of the button. If none of the fields other than <em>text</em>, <em>icon_custom_emoji_id</em>, and
  *   <em>style</em> are used, it will be sent as a message when the button is pressed.
  * @param iconCustomEmojiId
  *   <em>Optional</em>. Unique identifier of the custom emoji shown before the text of the button. Can only be used by
  *   bots that purchased additional usernames on <a href="https://fragment.com">Fragment</a> or in the messages
  *   directly sent by the bot to private, group and supergroup chats if the owner of the bot has a Telegram Premium
  *   subscription.
  * @param style
  *   <em>Optional</em>. Style of the button. Must be one of “danger” (red), “success” (green) or “primary” (blue). If
  *   omitted, then an app-specific style is used.
  * @param requestUsers
  *   <em>Optional</em>. If specified, pressing the button will open a list of suitable users. Identifiers of selected
  *   users will be sent to the bot in a “users_shared” service message. Available in private chats only.
  * @param requestChat
  *   <em>Optional</em>. If specified, pressing the button will open a list of suitable chats. Tapping on a chat will
  *   send its identifier to the bot in a “chat_shared” service message. Available in private chats only.
  * @param requestManagedBot
  *   <em>Optional</em>. If specified, pressing the button will ask the user to create and share a bot that will be
  *   managed by the current bot. Available for bots that enabled management of other bots in the <a
  *   href="https://t.me/BotFather">&#064;BotFather</a> Mini App. Available in private chats only.
  * @param requestContact
  *   <em>Optional</em>. If <em>True</em>, the user's phone number will be sent as a contact when the button is pressed.
  *   Available in private chats only.
  * @param requestLocation
  *   <em>Optional</em>. If <em>True</em>, the user's current location will be sent when the button is pressed.
  *   Available in private chats only.
  * @param requestPoll
  *   <em>Optional</em>. If specified, the user will be asked to create a poll and send it to the bot when the button is
  *   pressed. Available in private chats only.
  * @param webApp
  *   <em>Optional</em>. If specified, the described <a href="https://core.telegram.org/bots/webapps">Web App</a> will
  *   be launched when the button is pressed. The Web App will be able to send a “web_app_data” service message.
  *   Available in private chats only.
  */
final case class KeyboardButton(
  text: String,
  iconCustomEmojiId: Option[String] = Option.empty,
  style: Option[String] = Option.empty,
  requestUsers: Option[KeyboardButtonRequestUsers] = Option.empty,
  requestChat: Option[KeyboardButtonRequestChat] = Option.empty,
  requestManagedBot: Option[KeyboardButtonRequestManagedBot] = Option.empty,
  requestContact: Option[Boolean] = Option.empty,
  requestLocation: Option[Boolean] = Option.empty,
  requestPoll: Option[KeyboardButtonPollType] = Option.empty,
  webApp: Option[WebAppInfo] = Option.empty
)
