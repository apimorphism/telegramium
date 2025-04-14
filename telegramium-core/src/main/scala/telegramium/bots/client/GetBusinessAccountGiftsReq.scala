package telegramium.bots.client

/** @param businessConnectionId
  *   Unique identifier of the business connection
  * @param excludeUnsaved
  *   Pass True to exclude gifts that aren't saved to the account's profile page
  * @param excludeSaved
  *   Pass True to exclude gifts that are saved to the account's profile page
  * @param excludeUnlimited
  *   Pass True to exclude gifts that can be purchased an unlimited number of times
  * @param excludeLimited
  *   Pass True to exclude gifts that can be purchased a limited number of times
  * @param excludeUnique
  *   Pass True to exclude unique gifts
  * @param sortByPrice
  *   Pass True to sort results by gift price instead of send date. Sorting is applied before pagination.
  * @param offset
  *   Offset of the first entry to return as received from the previous request; use empty string to get the first chunk
  *   of results
  * @param limit
  *   The maximum number of gifts to be returned; 1-100. Defaults to 100
  */
final case class GetBusinessAccountGiftsReq(
  businessConnectionId: String,
  excludeUnsaved: Option[Boolean] = Option.empty,
  excludeSaved: Option[Boolean] = Option.empty,
  excludeUnlimited: Option[Boolean] = Option.empty,
  excludeLimited: Option[Boolean] = Option.empty,
  excludeUnique: Option[Boolean] = Option.empty,
  sortByPrice: Option[Boolean] = Option.empty,
  offset: Option[String] = Option.empty,
  limit: Option[Int] = Option.empty
)
