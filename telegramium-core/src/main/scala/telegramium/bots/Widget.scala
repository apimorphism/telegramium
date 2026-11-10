package telegramium.bots

/** This object describes an <a href="https://core.telegram.org/bots/widgets">HTML widget</a>.
  *
  * @param html
  *   HTML code of the widget
  * @param id
  *   <em>Optional</em>. Unique identifier of the shared widget, which can be used to send the widget again using the
  *   method <a href="https://core.telegram.org/bots/api#sendsharedwidget">sendSharedWidget</a>; omitted if the widget
  *   isn't shared
  * @param width
  *   <em>Optional</em>. Width of the widget in logical pixels; omitted if the widget must occupy the whole width of the
  *   message bubble
  * @param height
  *   <em>Optional</em>. Height of the widget in logical pixels; omitted if the height of the widget isn't fixed
  * @param publicData
  *   <em>Optional</em>. Public data of the widget, which is available wherever the widget is shown. All messages with a
  *   shared widget have the same public data.
  * @param chatData
  *   <em>Optional</em>. Data of the widget, which is specific to the message
  */
final case class Widget(
  html: String,
  id: Option[String] = Option.empty,
  width: Option[Int] = Option.empty,
  height: Option[Int] = Option.empty,
  publicData: Option[String] = Option.empty,
  chatData: Option[String] = Option.empty
)
