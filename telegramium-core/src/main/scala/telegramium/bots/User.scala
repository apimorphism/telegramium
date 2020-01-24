package telegramium.bots

/** This object represents a Telegram user or bot.*/
final case class User(
                      /** Unique identifier for this user or bot*/
                      id: Int,
                      /** True, if this user is a bot*/
                      isBot: Boolean,
                      /** User‘s or bot’s first name*/
                      firstName: String,
                      /** Optional. User‘s or bot’s last name*/
                      lastName: Option[String] = Option.empty,
                      /** Optional. User‘s or bot’s username*/
                      username: Option[String] = Option.empty,
                      /** Optional. IETF language tag of the user's language*/
                      languageCode: Option[String] = Option.empty,
                      /** Optional. True, if the bot can be invited to groups.
                        * Returned only in getMe.*/
                      canJoinGroups: Option[Boolean] = Option.empty,
                      /** Optional. True, if privacy mode is disabled for the bot.
                        * Returned only in getMe.*/
                      canReadAllGroupMessages: Option[Boolean] = Option.empty,
                      /** Optional. True, if the bot supports inline queries.
                        * Returned only in getMe.*/
                      supportsInlineQueries: Option[Boolean] = Option.empty)
