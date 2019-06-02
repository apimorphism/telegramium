package io.github.fperiodic.apimorphism.telegramium.bots.client

final case class SetGameScoreReq(
                                 /** User identifier*/
                                 userId: Int,
                                 /** New score, must be non-negative*/
                                 score: Int,
                                 /** Pass True, if the high score is allowed to decrease. This
                                   * can be useful when fixing mistakes or banning cheaters*/
                                 force: Option[Boolean] = Option.empty,
                                 /** Pass True, if the game message should not be automatically
                                   * edited to include the current scoreboard*/
                                 disableEditMessage: Option[Boolean] = Option.empty,
                                 /** Required if inline_message_id is not specified. Unique
                                   * identifier for the target chat*/
                                 chatId: Option[Int] = Option.empty,
                                 /** Required if inline_message_id is not specified. Identifier
                                   * of the sent message*/
                                 messageId: Option[Int] = Option.empty,
                                 /** Required if chat_id and message_id are not specified.
                                   * Identifier of the inline message*/
                                 inlineMessageId: Option[String] = Option.empty)
