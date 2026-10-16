package telegramium.bots.client

/** @param userId
  *   User identifier
  * @param score
  *   New score, must be non-negative
  * @param force
  *   Pass <em>True</em> if the high score is allowed to decrease. This can be useful when fixing mistakes or banning
  *   cheaters.
  * @param disableEditMessage
  *   Pass <em>True</em> if the game message should not be automatically edited to include the current scoreboard
  * @param chatId
  *   Required if <em>inline_message_id</em> is not specified. Unique identifier for the target chat.
  * @param messageId
  *   Required if <em>inline_message_id</em> is not specified. Identifier of the sent message.
  * @param inlineMessageId
  *   Required if <em>chat_id</em> and <em>message_id</em> are not specified. Identifier of the inline message.
  */
final case class SetGameScoreReq(
  userId: Long,
  score: Int,
  force: Option[Boolean] = Option.empty,
  disableEditMessage: Option[Boolean] = Option.empty,
  chatId: Option[Long] = Option.empty,
  messageId: Option[Int] = Option.empty,
  inlineMessageId: Option[String] = Option.empty
)
