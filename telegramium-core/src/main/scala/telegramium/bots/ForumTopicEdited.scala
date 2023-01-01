package telegramium.bots

/** This object represents a service message about an edited forum topic.
  *
  * @param name
  *   Optional. New name of the topic, if it was edited
  * @param iconCustomEmojiId
  *   Optional. New identifier of the custom emoji shown as the topic icon, if it was edited; an empty string if the
  *   icon was removed
  */
final case class ForumTopicEdited(name: Option[String] = Option.empty, iconCustomEmojiId: Option[String] = Option.empty)
