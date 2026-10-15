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
  * @param canJoinGroups
  *   <em>Optional</em>. <em>True</em>, if the bot can be invited to groups. Returned only in <a
  *   href="https://core.telegram.org/bots/api#getme">getMe</a>.
  * @param canReadAllGroupMessages
  *   <em>Optional</em>. <em>True</em>, if <a href="https://core.telegram.org/bots/features#privacy-mode">privacy
  *   mode</a> is disabled for the bot. Returned only in <a href="https://core.telegram.org/bots/api#getme">getMe</a>.
  * @param supportsGuestQueries
  *   <em>Optional</em>. <em>True</em>, if the bot supports guest queries from chats it is not a member of. Returned
  *   only in <a href="https://core.telegram.org/bots/api#getme">getMe</a>.
  * @param supportsInlineQueries
  *   <em>Optional</em>. <em>True</em>, if the bot supports inline queries. Returned only in <a
  *   href="https://core.telegram.org/bots/api#getme">getMe</a>.
  * @param canConnectToBusiness
  *   <em>Optional</em>. <em>True</em>, if the bot can be connected to a user account to manage it. Returned only in <a
  *   href="https://core.telegram.org/bots/api#getme">getMe</a>.
  * @param hasMainWebApp
  *   <em>Optional</em>. <em>True</em>, if the bot has a main Web App. Returned only in <a
  *   href="https://core.telegram.org/bots/api#getme">getMe</a>.
  * @param hasTopicsEnabled
  *   <em>Optional</em>. <em>True</em>, if the bot has forum topic mode enabled in private chats. Returned only in <a
  *   href="https://core.telegram.org/bots/api#getme">getMe</a>.
  * @param allowsUsersToCreateTopics
  *   <em>Optional</em>. <em>True</em>, if the bot allows users to create and delete topics in private chats. Returned
  *   only in <a href="https://core.telegram.org/bots/api#getme">getMe</a>.
  * @param canManageBots
  *   <em>Optional</em>. <em>True</em>, if other bots can be created to be controlled by the bot. Returned only in <a
  *   href="https://core.telegram.org/bots/api#getme">getMe</a>.
  * @param supportsJoinRequestQueries
  *   <em>Optional</em>. <em>True</em>, if the bot supports join request queries and can be assigned to process them.
  *   Returned only in <a href="https://core.telegram.org/bots/api#getme">getMe</a>.
  */
final case class User(
  id: Long,
  isBot: Boolean,
  firstName: String,
  lastName: Option[String] = Option.empty,
  username: Option[String] = Option.empty,
  languageCode: Option[String] = Option.empty,
  isPremium: Option[Boolean] = Option.empty,
  addedToAttachmentMenu: Option[Boolean] = Option.empty,
  canJoinGroups: Option[Boolean] = Option.empty,
  canReadAllGroupMessages: Option[Boolean] = Option.empty,
  supportsGuestQueries: Option[Boolean] = Option.empty,
  supportsInlineQueries: Option[Boolean] = Option.empty,
  canConnectToBusiness: Option[Boolean] = Option.empty,
  hasMainWebApp: Option[Boolean] = Option.empty,
  hasTopicsEnabled: Option[Boolean] = Option.empty,
  allowsUsersToCreateTopics: Option[Boolean] = Option.empty,
  canManageBots: Option[Boolean] = Option.empty,
  supportsJoinRequestQueries: Option[Boolean] = Option.empty
)
