package telegramium.bots.client

/** @param widgetId
  *   Unique identifier of the shared widget
  * @param publicData
  *   New public data of the widget, 0-1024 bytes; pass an empty string to remove the public data
  * @param cacheTime
  *   The maximum amount of time in seconds that the public data of the widget may be cached client-side; 5-3600. If
  *   omitted, then the current value is kept, which is 30 by default.
  */
final case class EditSharedWidgetDataReq(widgetId: String, publicData: String, cacheTime: Option[Int] = Option.empty)
