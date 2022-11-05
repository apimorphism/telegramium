package telegramium.bots

/** This object represents a service message about a new forum topic created in the chat.
  *
  * @param name
  *   Name of the topic
  * @param iconColor
  *   Color of the topic icon in RGB format
  * @param iconCustomEmojiId
  *   Optional. Unique identifier of the custom emoji shown as the topic icon
  */
final case class ForumTopicCreated(name: String, iconColor: Int, iconCustomEmojiId: Option[String] = Option.empty)
