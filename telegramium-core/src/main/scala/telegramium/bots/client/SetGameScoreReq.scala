package telegramium.bots.client

/** @param userId
  *   User identifier
  * @param score
  *   New score, must be non-negative
  * @param force
  *   Pass True, if the high score is allowed to decrease. This can be useful when fixing mistakes or banning cheaters
  * @param disableEditMessage
  *   Pass True, if the game message should not be automatically edited to include the current scoreboard
  * @param chatId
  *   Required if inline_message_id is not specified. Unique identifier for the target chat
  * @param messageId
  *   Required if inline_message_id is not specified. Identifier of the sent message
  * @param inlineMessageId
  *   Required if chat_id and message_id are not specified. Identifier of the inline message
  */
final case class SetGameScoreReq(
  userId: Int,
  score: Int,
  force: Option[Boolean] = Option.empty,
  disableEditMessage: Option[Boolean] = Option.empty,
  chatId: Option[Int] = Option.empty,
  messageId: Option[Int] = Option.empty,
  inlineMessageId: Option[String] = Option.empty
)
