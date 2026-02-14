package telegramium.bots

/** This object represents one button of the reply keyboard. At most one of the fields other than text,
  * icon_custom_emoji_id, and style must be used to specify the type of the button. For simple text buttons, String can
  * be used instead of this object to specify the button text.
  *
  * @param text
  *   Text of the button. If none of the fields other than text, icon_custom_emoji_id, and style are used, it will be
  *   sent as a message when the button is pressed
  * @param iconCustomEmojiId
  *   Optional. Unique identifier of the custom emoji shown before the text of the button. Can only be used by bots that
  *   purchased additional usernames on Fragment or in the messages directly sent by the bot to private, group and
  *   supergroup chats if the owner of the bot has a Telegram Premium subscription.
  * @param style
  *   Optional. Style of the button. Must be one of “danger” (red), “success” (green) or “primary” (blue). If omitted,
  *   then an app-specific style is used.
  * @param requestUsers
  *   Optional. If specified, pressing the button will open a list of suitable users. Identifiers of selected users will
  *   be sent to the bot in a “users_shared” service message. Available in private chats only.
  * @param requestChat
  *   Optional. If specified, pressing the button will open a list of suitable chats. Tapping on a chat will send its
  *   identifier to the bot in a “chat_shared” service message. Available in private chats only.
  * @param requestContact
  *   Optional. If True, the user's phone number will be sent as a contact when the button is pressed. Available in
  *   private chats only.
  * @param requestLocation
  *   Optional. If True, the user's current location will be sent when the button is pressed. Available in private chats
  *   only.
  * @param requestPoll
  *   Optional. If specified, the user will be asked to create a poll and send it to the bot when the button is pressed.
  *   Available in private chats only.
  * @param webApp
  *   Optional. If specified, the described Web App will be launched when the button is pressed. The Web App will be
  *   able to send a “web_app_data” service message. Available in private chats only.
  */
final case class KeyboardButton(
  text: String,
  iconCustomEmojiId: Option[String] = Option.empty,
  style: Option[String] = Option.empty,
  requestUsers: Option[KeyboardButtonRequestUsers] = Option.empty,
  requestChat: Option[KeyboardButtonRequestChat] = Option.empty,
  requestContact: Option[Boolean] = Option.empty,
  requestLocation: Option[Boolean] = Option.empty,
  requestPoll: Option[KeyboardButtonPollType] = Option.empty,
  webApp: Option[WebAppInfo] = Option.empty
)
