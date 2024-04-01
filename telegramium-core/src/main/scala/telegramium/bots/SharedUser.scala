package telegramium.bots

/** This object contains information about a user that was shared with the bot using a KeyboardButtonRequestUser button.
  *
  * @param userId
  *   Identifier of the shared user. This number may have more than 32 significant bits and some programming languages
  *   may have difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so 64-bit integers
  *   or double-precision float types are safe for storing these identifiers. The bot may not have access to the user
  *   and could be unable to use this identifier, unless the user is already known to the bot by some other means.
  * @param firstName
  *   Optional. First name of the user, if the name was requested by the bot
  * @param lastName
  *   Optional. Last name of the user, if the name was requested by the bot
  * @param username
  *   Optional. Username of the user, if the username was requested by the bot
  * @param photo
  *   Optional. Available sizes of the chat photo, if the photo was requested by the bot
  */
final case class SharedUser(
  userId: Long,
  firstName: Option[String] = Option.empty,
  lastName: Option[String] = Option.empty,
  username: Option[String] = Option.empty,
  photo: List[PhotoSize] = List.empty
)
