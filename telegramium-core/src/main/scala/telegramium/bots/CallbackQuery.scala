package telegramium.bots

/** This object represents an incoming callback query from a callback button in an <a
  * href="https://core.telegram.org/bots/features#inline-keyboards">inline keyboard</a> or in a <a
  * href="https://core.telegram.org/bots/api#richmessage">rich message</a>. If the button that originated the query
  * belongs to a message sent by the bot, the field <em>message</em> will be present, unless the field
  * <em>inline_message_id</em> is used instead as specified by the parameter <em>callback_query_mode</em> of the method
  * <a href="https://core.telegram.org/bots/api#setmysettings">setMySettings</a>. If the button belongs to a message
  * sent via the bot (in <a href="https://core.telegram.org/bots/api#inline-mode">inline mode</a>), the field
  * <em>inline_message_id</em> will be present. Exactly one of the fields <em>data</em> or <em>game_short_name</em> will
  * be present.
  *
  * <strong>NOTE:</strong> After the user presses a callback button, Telegram clients will display a progress bar until
  * you call <a href="https://core.telegram.org/bots/api#answercallbackquery">answerCallbackQuery</a>. It is, therefore,
  * necessary to react by calling <a
  * href="https://core.telegram.org/bots/api#answercallbackquery">answerCallbackQuery</a> even if no notification to the
  * user is needed (e.g., without specifying any of the optional parameters).
  *
  * @param id
  *   Unique identifier for this query
  * @param from
  *   Sender
  * @param chatInstance
  *   Global identifier, uniquely corresponding to the chat to which the message with the callback button was sent.
  *   Useful for high scores in <a href="https://core.telegram.org/bots/api#games">games</a>.
  * @param message
  *   <em>Optional</em>. Message sent by the bot with the callback button that originated the query
  * @param inlineMessageId
  *   <em>Optional</em>. Identifier of the message sent via the bot in inline mode, that originated the query. Can also
  *   be present for messages sent by the bot, depending on the parameter <em>callback_query_mode</em> of the method <a
  *   href="https://core.telegram.org/bots/api#setmysettings">setMySettings</a>.
  * @param data
  *   <em>Optional</em>. Data associated with the callback button. Be aware that the message originated the query can
  *   contain no callback buttons with this data.
  * @param gameShortName
  *   <em>Optional</em>. Short name of a <a href="https://core.telegram.org/bots/api#game">Game</a> to be returned,
  *   serves as the unique identifier for the game
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
