package telegramium.bots.client

final case class GetGameHighScoresReq(
                                      /** Target user id*/
                                      userId: Int,
                                      /** Required if inline_message_id is not specified. Unique
                                        * identifier for the target chat*/
                                      chatId: Option[Int] = Option.empty,
                                      /** Required if inline_message_id is not specified. Identifier
                                        * of the sent message*/
                                      messageId: Option[Int] = Option.empty,
                                      /** Required if chat_id and message_id are not specified.
                                        * Identifier of the inline message*/
                                      inlineMessageId: Option[String] = Option.empty)
