package telegramium.bots

/** This object is received when messages are deleted from a connected business account.
  *
  * @param businessConnectionId
  *   Unique identifier of the business connection
  * @param chat
  *   Information about a chat in the business account. The bot may not have access to the chat or the corresponding
  *   user.
  * @param messageIds
  *   The list of identifiers of deleted messages in the chat of the business account
  */
final case class BusinessMessagesDeleted(businessConnectionId: String, chat: Chat, messageIds: List[Int] = List.empty)
