package telegramium.bots.client

import telegramium.bots.InlineQueryResult
import telegramium.bots.InlineQueryResultsButton

/** @param inlineQueryId
  *   Unique identifier for the answered query
  * @param results
  *   A JSON-serialized array of results for the inline query
  * @param cacheTime
  *   The maximum amount of time in seconds that the result of the inline query may be cached on the server. Defaults to
  *   300.
  * @param isPersonal
  *   Pass True if results may be cached on the server side only for the user that sent the query. By default, results
  *   may be returned to any user who sends the same query.
  * @param nextOffset
  *   Pass the offset that a client should send in the next query with the same text to receive more results. Pass an
  *   empty string if there are no more results or if you don't support pagination. Offset length can't exceed 64 bytes.
  * @param button
  *   A JSON-serialized object describing a button to be shown above inline query results
  */
final case class AnswerInlineQueryReq(
  inlineQueryId: String,
  results: List[InlineQueryResult] = List.empty,
  cacheTime: Option[Int] = Option.empty,
  isPersonal: Option[Boolean] = Option.empty,
  nextOffset: Option[String] = Option.empty,
  button: Option[InlineQueryResultsButton] = Option.empty
)
