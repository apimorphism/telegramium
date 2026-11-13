package telegramium.bots

/** This object represents an incoming query from a forwarded message with a shared widget created by the bot. Use <a
  * href="https://core.telegram.org/bots/api#answerwidgetquery">answerWidgetQuery</a> to answer the query. Each user can
  * send at most 5 queries per 5 seconds to the bot from all its widgets.
  *
  * @param id
  *   Unique identifier for this query
  * @param from
  *   Sender
  * @param type
  *   Type of the query
  * @param data
  *   Data of the query, 1-64 bytes
  * @param widgetId
  *   Unique identifier of the shared widget, which sent the query
  */
final case class SharedWidgetQuery(
  id: String,
  from: User,
  `type`: iozhik.OpenUnion[WidgetQueryType],
  data: String,
  widgetId: String
)
