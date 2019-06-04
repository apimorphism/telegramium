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
                      languageCode: Option[String] = Option.empty)
