package telegramium.bots.client

/** @param offset
  *   Number of transactions to skip in the response
  * @param limit
  *   The maximum number of transactions to be retrieved. Values between 1-100 are accepted. Defaults to 100.
  */
final case class GetStarTransactionsReq(offset: Option[Int] = Option.empty, limit: Option[Int] = Option.empty)
