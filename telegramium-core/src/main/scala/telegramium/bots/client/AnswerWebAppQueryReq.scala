package telegramium.bots.client

import telegramium.bots.InlineQueryResult

/** @param webAppQueryId
  *   Unique identifier for the query to be answered
  * @param result
  *   A JSON-serialized object describing the message to be sent
  */
final case class AnswerWebAppQueryReq(webAppQueryId: String, result: InlineQueryResult)
