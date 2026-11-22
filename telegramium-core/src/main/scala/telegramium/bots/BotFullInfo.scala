package telegramium.bots

/** This object contains full information about the bot itself.
  *
  * @param id
  *   Unique identifier for this bot. This number may have more than 32 significant bits and some programming languages
  *   may have difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a 64-bit integer
  *   or double-precision float type are safe for storing this identifier.
  * @param isBot
  *   Always <em>True</em>
  * @param firstName
  *   Bot's first name
  * @param canJoinGroups
  *   <em>True</em>, if the bot can be added to groups
  * @param canReadAllGroupMessages
  *   <em>True</em>, if <a href="https://core.telegram.org/bots/features#privacy-mode">privacy mode</a> is disabled for
  *   the bot
  * @param supportsGuestQueries
  *   <em>True</em>, if the bot supports guest queries from chats it is not a member of
  * @param supportsInlineQueries
  *   <em>True</em>, if the bot supports inline queries
  * @param needsInlineQueryLocation
  *   <em>True</em>, if the bot requests the location of the user with every inline query
  * @param canConnectToBusiness
  *   <em>True</em>, if the bot can be connected to a user account to manage it
  * @param hasMainWebApp
  *   <em>True</em>, if the bot has a main Web App
  * @param hasTopicsEnabled
  *   <em>True</em>, if the bot has forum topic mode enabled in private chats
  * @param allowsUsersToCreateTopics
  *   <em>True</em>, if the bot allows users to create and delete topics in private chats
  * @param hasTopicSidebar
  *   <em>True</em>, if topics in private chats with the bot are shown in a sidebar
  * @param canManageBots
  *   <em>True</em>, if other bots can be created to be controlled by the bot
  * @param supportsJoinRequestQueries
  *   <em>True</em>, if the bot supports join request queries and can be assigned to process them
  * @param username
  *   <em>Optional</em>. Bot's username
  * @param activeUsernames
  *   <em>Optional</em>. If non-empty, the list of all <a
  *   href="https://telegram.org/blog/topics-in-groups-collectible-usernames#collectible-usernames">active usernames</a>
  *   of the bot
  * @param inlineQueryPlaceholder
  *   <em>Optional</em>. Placeholder for inline queries, which is shown in the input field after the bot's username;
  *   omitted if the default placeholder is used
  */
final case class BotFullInfo(
  id: Long,
  isBot: Boolean,
  firstName: String,
  canJoinGroups: Boolean,
  canReadAllGroupMessages: Boolean,
  supportsGuestQueries: Boolean,
  supportsInlineQueries: Boolean,
  needsInlineQueryLocation: Boolean,
  canConnectToBusiness: Boolean,
  hasMainWebApp: Boolean,
  hasTopicsEnabled: Boolean,
  allowsUsersToCreateTopics: Boolean,
  hasTopicSidebar: Boolean,
  canManageBots: Boolean,
  supportsJoinRequestQueries: Boolean,
  username: Option[String] = Option.empty,
  activeUsernames: List[String] = List.empty,
  inlineQueryPlaceholder: Option[String] = Option.empty
)
