package telegramium.bots

/** Describes the connection of the bot with a business account.
  *
  * @param id
  *   Unique identifier of the business connection
  * @param user
  *   Business account user that created the business connection
  * @param userChatId
  *   Identifier of a private chat with the user who created the business connection. This number may have more than 32
  *   significant bits and some programming languages may have difficulty/silent defects in interpreting it. But it has
  *   at most 52 significant bits, so a 64-bit integer or double-precision float type are safe for storing this
  *   identifier.
  * @param date
  *   Date the connection was established in Unix time
  * @param isEnabled
  *   True, if the connection is active
  * @param rights
  *   Optional. Rights of the business bot
  */
final case class BusinessConnection(
  id: String,
  user: User,
  userChatId: Long,
  date: Long,
  isEnabled: Boolean,
  rights: Option[BusinessBotRights] = Option.empty
)
