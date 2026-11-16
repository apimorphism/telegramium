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
  *   <em>Optional</em>. Title, for supergroups, channels and group chats
  * @param username
  *   <em>Optional</em>. Username, for private chats, supergroups and channels if available
  * @param firstName
  *   <em>Optional</em>. First name of the other party in a private chat
  * @param lastName
  *   <em>Optional</em>. Last name of the other party in a private chat
  * @param isForum
  *   <em>Optional</em>. <em>True</em>, if the supergroup chat is a forum (has <a
  *   href="https://telegram.org/blog/topics-in-groups-collectible-usernames#topics-in-groups">topics</a> enabled)
  * @param isDirectMessages
  *   <em>Optional</em>. <em>True</em>, if the chat is the direct messages chat of a channel
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
