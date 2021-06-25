package telegramium.bots

/**
 * This object represents a Telegram user or bot.
 *
 * @param id Unique identifier for this user or bot. This number may
 * have more than 32 significant bits and some programming
 * languages may have difficulty/silent defects in interpreting
 * it. But it has at most 52 significant bits, so a 64-bit
 * integer or double-precision float type are safe for storing
 * this identifier.
 * @param isBot True, if this user is a bot
 * @param firstName User's or bot's first name
 * @param lastName Optional. User's or bot's last name
 * @param username Optional. User's or bot's username
 * @param languageCode Optional. IETF language tag of the user's language
 * @param canJoinGroups Optional. True, if the bot can be invited to groups.
 * Returned only in getMe.
 * @param canReadAllGroupMessages Optional. True, if privacy mode is disabled for the bot.
 * Returned only in getMe.
 * @param supportsInlineQueries Optional. True, if the bot supports inline queries.
 * Returned only in getMe.
 */
final case class User(id: Long,
                      isBot: Boolean,
                      firstName: String,
                      lastName: Option[String] = Option.empty,
                      username: Option[String] = Option.empty,
                      languageCode: Option[String] = Option.empty,
                      canJoinGroups: Option[Boolean] = Option.empty,
                      canReadAllGroupMessages: Option[Boolean] = Option.empty,
                      supportsInlineQueries: Option[Boolean] = Option.empty
)
