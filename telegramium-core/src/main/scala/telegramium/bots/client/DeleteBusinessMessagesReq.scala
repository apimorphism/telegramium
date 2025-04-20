package telegramium.bots.client

/** @param businessConnectionId
  *   Unique identifier of the business connection on behalf of which to delete the messages
  * @param messageIds
  *   A JSON-serialized list of 1-100 identifiers of messages to delete. All messages must be from the same chat. See
  *   deleteMessage for limitations on which messages can be deleted
  */
final case class DeleteBusinessMessagesReq(businessConnectionId: String, messageIds: List[Int] = List.empty)
