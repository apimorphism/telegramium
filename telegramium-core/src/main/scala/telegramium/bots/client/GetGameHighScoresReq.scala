package telegramium.bots.client

/** @param userId
  *   Target user id
  * @param chatId
  *   Required if <em>inline_message_id</em> is not specified. Unique identifier for the target chat.
  * @param messageId
  *   Required if <em>inline_message_id</em> is not specified. Identifier of the sent message.
  * @param inlineMessageId
  *   Required if <em>chat_id</em> and <em>message_id</em> are not specified. Identifier of the inline message.
  */
final case class GetGameHighScoresReq(
  userId: Long,
  chatId: Option[Long] = Option.empty,
  messageId: Option[Int] = Option.empty,
  inlineMessageId: Option[String] = Option.empty
)
