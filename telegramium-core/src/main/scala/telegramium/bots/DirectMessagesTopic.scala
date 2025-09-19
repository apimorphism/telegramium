package telegramium.bots

/** Describes a topic of a direct messages chat.
  *
  * @param topicId
  *   Unique identifier of the topic. This number may have more than 32 significant bits and some programming languages
  *   may have difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a 64-bit integer
  *   or double-precision float type are safe for storing this identifier.
  * @param user
  *   Optional. Information about the user that created the topic. Currently, it is always present
  */
final case class DirectMessagesTopic(topicId: Long, user: Option[User] = Option.empty)
