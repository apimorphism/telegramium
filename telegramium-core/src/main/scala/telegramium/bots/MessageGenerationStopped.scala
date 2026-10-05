package telegramium.bots

/** This object describes an update about a user stopping message generation.
  *
  * @param chat
  *   Chat in which the message is generated
  * @param draftId
  *   Unique identifier of the message draft which was stopped
  * @param messageThreadId
  *   Optional. Unique identifier of the message thread in which the message is generated
  */
final case class MessageGenerationStopped(chat: Chat, draftId: Int, messageThreadId: Option[Int] = Option.empty)
