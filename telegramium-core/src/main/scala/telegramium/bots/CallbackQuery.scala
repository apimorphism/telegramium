package telegramium.bots

/** This object represents an incoming callback query from a callback button in an inline keyboard. If the button that
  * originated the query was attached to a message sent by the bot, the field message will be present. If the button was
  * attached to a message sent via the bot (in inline mode), the field inline_message_id will be present. Exactly one of
  * the fields data or game_short_name will be present.
  *
  * @param id
  *   Unique identifier for this query
  * @param from
  *   Sender
  * @param chatInstance
  *   Global identifier, uniquely corresponding to the chat to which the message with the callback button was sent.
  *   Useful for high scores in games.
  * @param message
  *   Optional. Message sent by the bot with the callback button that originated the query
  * @param inlineMessageId
  *   Optional. Identifier of the message sent via the bot in inline mode, that originated the query.
  * @param data
  *   Optional. Data associated with the callback button. Be aware that the message originated the query can contain no
  *   callback buttons with this data.
  * @param gameShortName
  *   Optional. Short name of a Game to be returned, serves as the unique identifier for the game
  */
final case class CallbackQuery(
  id: String,
  from: User,
  chatInstance: String,
  message: Option[MaybeInaccessibleMessage] = Option.empty,
  inlineMessageId: Option[String] = Option.empty,
  data: Option[String] = Option.empty,
  gameShortName: Option[String] = Option.empty
)
