package telegramium.bots

/** This object represents a Telegram user or bot.
  *
  * @param id
  *   Unique identifier for this user or bot. This number may have more than 32 significant bits and some programming
  *   languages may have difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a
  *   64-bit integer or double-precision float type are safe for storing this identifier.
  * @param isBot
  *   <em>True</em>, if this user is a bot
  * @param firstName
  *   User's or bot's first name
  * @param lastName
  *   <em>Optional</em>. User's or bot's last name
  * @param username
  *   <em>Optional</em>. User's or bot's username
  * @param languageCode
  *   <em>Optional</em>. <a href="https://en.wikipedia.org/wiki/IETF_language_tag">IETF language tag</a> of the user's
  *   language
  * @param isPremium
  *   <em>Optional</em>. <em>True</em>, if this user is a Telegram Premium user
  * @param addedToAttachmentMenu
  *   <em>Optional</em>. <em>True</em>, if this user added the bot to the attachment menu
  */
final case class User(
  id: Long,
  isBot: Boolean,
  firstName: String,
  lastName: Option[String] = Option.empty,
  username: Option[String] = Option.empty,
  languageCode: Option[String] = Option.empty,
  isPremium: Option[Boolean] = Option.empty,
  addedToAttachmentMenu: Option[Boolean] = Option.empty
)
