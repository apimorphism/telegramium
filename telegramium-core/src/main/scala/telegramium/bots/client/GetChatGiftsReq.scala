package telegramium.bots.client

import telegramium.bots.ChatId

/** @param chatId
  *   Unique identifier for the target chat or username of the target channel in the format <code>&#064;username</code>
  * @param excludeUnsaved
  *   Pass <em>True</em> to exclude gifts that aren't saved to the chat's profile page. Always <em>True</em>, unless the
  *   bot has the <em>can_post_messages</em> administrator right in the channel.
  * @param excludeSaved
  *   Pass <em>True</em> to exclude gifts that are saved to the chat's profile page. Always <em>False</em>, unless the
  *   bot has the <em>can_post_messages</em> administrator right in the channel.
  * @param excludeUnlimited
  *   Pass <em>True</em> to exclude gifts that can be purchased an unlimited number of times
  * @param excludeLimitedUpgradable
  *   Pass <em>True</em> to exclude gifts that can be purchased a limited number of times and can be upgraded to unique
  * @param excludeLimitedNonUpgradable
  *   Pass <em>True</em> to exclude gifts that can be purchased a limited number of times and can't be upgraded to
  *   unique
  * @param excludeFromBlockchain
  *   Pass <em>True</em> to exclude gifts that were assigned from the TON blockchain and can't be resold or transferred
  *   in Telegram
  * @param excludeUnique
  *   Pass <em>True</em> to exclude unique gifts
  * @param sortByPrice
  *   Pass <em>True</em> to sort results by gift price instead of send date. Sorting is applied before pagination.
  * @param offset
  *   Offset of the first entry to return as received from the previous request; use an empty string to get the first
  *   chunk of results
  * @param limit
  *   The maximum number of gifts to be returned; 1-100. Defaults to 100.
  */
final case class GetChatGiftsReq(
  chatId: ChatId,
  excludeUnsaved: Option[Boolean] = Option.empty,
  excludeSaved: Option[Boolean] = Option.empty,
  excludeUnlimited: Option[Boolean] = Option.empty,
  excludeLimitedUpgradable: Option[Boolean] = Option.empty,
  excludeLimitedNonUpgradable: Option[Boolean] = Option.empty,
  excludeFromBlockchain: Option[Boolean] = Option.empty,
  excludeUnique: Option[Boolean] = Option.empty,
  sortByPrice: Option[Boolean] = Option.empty,
  offset: Option[String] = Option.empty,
  limit: Option[Int] = Option.empty
)
