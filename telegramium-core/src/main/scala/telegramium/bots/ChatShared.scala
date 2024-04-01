package telegramium.bots

/** This object contains information about a chat that was shared with the bot using a KeyboardButtonRequestChat button.
  *
  * @param requestId
  *   Identifier of the request
  * @param chatId
  *   Identifier of the shared chat. This number may have more than 32 significant bits and some programming languages
  *   may have difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a 64-bit integer
  *   or double-precision float type are safe for storing this identifier. The bot may not have access to the chat and
  *   could be unable to use this identifier, unless the chat is already known to the bot by some other means.
  * @param title
  *   Optional. Title of the chat, if the title was requested by the bot.
  * @param username
  *   Optional. Username of the chat, if the username was requested by the bot and available.
  * @param photo
  *   Optional. Available sizes of the chat photo, if the photo was requested by the bot
  */
final case class ChatShared(
  requestId: Int,
  chatId: Long,
  title: Option[String] = Option.empty,
  username: Option[String] = Option.empty,
  photo: List[PhotoSize] = List.empty
)
