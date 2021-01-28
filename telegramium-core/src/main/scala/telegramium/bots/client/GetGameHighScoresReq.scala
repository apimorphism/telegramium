package telegramium.bots.client

/** @param userId Target user id
  * @param chatId Required if inline_message_id is not specified. Unique
  * identifier for the target chat
  * @param messageId Required if inline_message_id is not specified. Identifier
  * of the sent message
  * @param inlineMessageId Required if chat_id and message_id are not specified.
  * Identifier of the inline message */
final case class GetGameHighScoresReq(userId: Int,
                                      chatId: Option[Int] = Option.empty,
                                      messageId: Option[Int] = Option.empty,
                                      inlineMessageId: Option[String] = Option.empty)
