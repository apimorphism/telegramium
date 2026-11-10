package telegramium.bots.client

/** @param widgetQueryId
  *   Unique identifier for the query to be answered
  * @param data
  *   Data to be returned to the widget, 0-1024 bytes
  */
final case class AnswerWidgetQueryReq(widgetQueryId: String, data: Option[String] = Option.empty)
