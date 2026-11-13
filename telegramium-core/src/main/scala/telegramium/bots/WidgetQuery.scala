package telegramium.bots

/** This object represents an incoming query from a widget in a message sent by the bot or via the bot. Use <a
  * href="https://core.telegram.org/bots/api#answerwidgetquery">answerWidgetQuery</a> to answer the query. Each user can
  * send at most 5 queries per 5 seconds to the bot from all its widgets.
  *
  * @param id
  *   Unique identifier for this query
  * @param from
  *   Sender
  * @param chatInstance
  *   Global identifier, uniquely corresponding to the chat to which the message with the widget was sent
  * @param type
  *   Type of the query
  * @param data
  *   Data of the query, 1-64 bytes
  * @param chat
  *   <em>Optional</em>. Chat with the message containing the widget; omitted if the widget is in an inline message
  * @param messageId
  *   <em>Optional</em>. Identifier of the message containing the widget; omitted if the widget is in an inline message
  * @param inlineMessageId
  *   <em>Optional</em>. Identifier of the inline message containing the widget
  * @param chatType
  *   <em>Optional</em>. For widgets in inline messages, type of the chat to which the message was sent. Can be either
  *   “sender” for a private chat with the inline query sender, “private”, “group”, “supergroup”, or “channel”.
  * @param widgetId
  *   <em>Optional</em>. Unique identifier of the shared widget, which sent the query; omitted if the widget isn't
  *   shared
  */
final case class WidgetQuery(
  id: String,
  from: User,
  chatInstance: String,
  `type`: iozhik.OpenUnion[WidgetQueryType],
  data: String,
  chat: Option[Chat] = Option.empty,
  messageId: Option[Int] = Option.empty,
  inlineMessageId: Option[String] = Option.empty,
  chatType: Option[String] = Option.empty,
  widgetId: Option[String] = Option.empty
)
