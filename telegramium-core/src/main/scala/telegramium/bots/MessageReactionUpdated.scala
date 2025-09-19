package telegramium.bots

/** This object represents a change of a reaction on a message performed by a user.
  *
  * @param chat
  *   The chat containing the message the user reacted to
  * @param messageId
  *   Unique identifier of the message inside the chat
  * @param date
  *   Date of the change in Unix time
  * @param user
  *   Optional. The user that changed the reaction, if the user isn't anonymous
  * @param actorChat
  *   Optional. The chat on behalf of which the reaction was changed, if the user is anonymous
  * @param oldReaction
  *   Previous list of reaction types that were set by the user
  * @param newReaction
  *   New list of reaction types that have been set by the user
  */
final case class MessageReactionUpdated(
  chat: Chat,
  messageId: Int,
  date: Long,
  user: Option[User] = Option.empty,
  actorChat: Option[Chat] = Option.empty,
  oldReaction: List[iozhik.OpenEnum[ReactionType]] = List.empty,
  newReaction: List[iozhik.OpenEnum[ReactionType]] = List.empty
)
