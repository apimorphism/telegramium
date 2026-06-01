package telegramium.bots.client

import telegramium.bots.InlineQueryResult

/** @param guestQueryId
  *   Unique identifier for the query to be answered
  * @param result
  *   A JSON-serialized object describing the message to be sent
  */
final case class AnswerGuestQueryReq(guestQueryId: String, result: InlineQueryResult)
