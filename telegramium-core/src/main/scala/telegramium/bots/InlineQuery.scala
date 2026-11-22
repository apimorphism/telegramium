package telegramium.bots

/** This object represents an incoming inline query. When the user sends an empty query, your bot could return some
  * default or trending results.
  *
  * @param id
  *   Unique identifier for this query
  * @param from
  *   Sender
  * @param query
  *   Text of the query (up to 256 characters)
  * @param offset
  *   Offset of the results to be returned, can be controlled by the bot
  * @param chatType
  *   <em>Optional</em>. Type of the chat from which the inline query was sent. Can be either “sender” for a private
  *   chat with the inline query sender, “private”, “group”, “supergroup”, or “channel”. The chat type should be always
  *   known for requests sent from official clients and most third-party clients, unless the request was sent from a
  *   secret chat.
  * @param location
  *   <em>Optional</em>. Sender location, only for bots that enabled user location requests via <a
  *   href="https://t.me/botfather">&#064;BotFather</a> or using the parameter <em>needs_inline_query_location</em> of
  *   the method <a href="https://core.telegram.org/bots/api#setmysettings">setMySettings</a>
  */
final case class InlineQuery(
  id: String,
  from: User,
  query: String,
  offset: String,
  chatType: Option[String] = Option.empty,
  location: Option[Location] = Option.empty
)
