package telegramium.bots.client

/** @param chatJoinRequestQueryId
  *   Unique identifier of the join request query
  * @param result
  *   Result of the query. Must be either “approve” to allow the user to join the chat, “decline” to disallow the user
  *   to join the chat, or “queue” to leave the decision to other administrators.
  */
final case class AnswerChatJoinRequestQueryReq(chatJoinRequestQueryId: String, result: String)
