package telegramium.bots

/** This object represents a chat.
  *
  * @param id
  *   Unique identifier for this chat. This number may have more than 32 significant bits and some programming languages
  *   may have difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a signed 64-bit
  *   integer or double-precision float type are safe for storing this identifier.
  * @param type
  *   Type of the chat, can be either “private”, “group”, “supergroup” or “channel”
  * @param title
  *   Optional. Title, for supergroups, channels and group chats
  * @param username
  *   Optional. Username, for private chats, supergroups and channels if available
  * @param firstName
  *   Optional. First name of the other party in a private chat
  * @param lastName
  *   Optional. Last name of the other party in a private chat
  * @param isForum
  *   Optional. True, if the supergroup chat is a forum (has topics enabled)
  * @param isDirectMessages
  *   Optional. True, if the chat is the direct messages chat of a channel
  */
final case class Chat(
  id: Long,
  `type`: String,
  title: Option[String] = Option.empty,
  username: Option[String] = Option.empty,
  firstName: Option[String] = Option.empty,
  lastName: Option[String] = Option.empty,
  isForum: Option[Boolean] = Option.empty,
  isDirectMessages: Option[Boolean] = Option.empty
)
