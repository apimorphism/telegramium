package telegramium.bots

/** This object represents reaction changes on a message with anonymous reactions.
  *
  * @param chat
  *   The chat containing the message
  * @param messageId
  *   Unique message identifier inside the chat
  * @param date
  *   Date of the change in Unix time
  * @param reactions
  *   List of reactions that are present on the message
  */
final case class MessageReactionCountUpdated(
  chat: Chat,
  messageId: Int,
  date: Int,
  reactions: List[ReactionCount] = List.empty
)
