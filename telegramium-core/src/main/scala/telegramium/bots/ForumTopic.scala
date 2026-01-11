package telegramium.bots

/** This object represents a forum topic.
  *
  * @param messageThreadId
  *   Unique identifier of the forum topic
  * @param name
  *   Name of the topic
  * @param iconColor
  *   Color of the topic icon in RGB format
  * @param iconCustomEmojiId
  *   Optional. Unique identifier of the custom emoji shown as the topic icon
  * @param isNameImplicit
  *   Optional. True, if the name of the topic wasn't specified explicitly by its creator and likely needs to be changed
  *   by the bot
  */
final case class ForumTopic(
  messageThreadId: Int,
  name: String,
  iconColor: Int,
  iconCustomEmojiId: Option[String] = Option.empty,
  isNameImplicit: Option[Boolean] = Option.empty
)
