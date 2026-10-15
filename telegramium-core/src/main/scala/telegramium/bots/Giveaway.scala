package telegramium.bots

/** This object represents a message about a scheduled giveaway.
  *
  * @param winnersSelectionDate
  *   Point in time (Unix timestamp) when winners of the giveaway will be selected
  * @param winnerCount
  *   The number of users which are supposed to be selected as winners of the giveaway
  * @param chats
  *   The list of chats which the user must join to participate in the giveaway
  * @param onlyNewMembers
  *   <em>Optional</em>. <em>True</em>, if only users who join the chats after the giveaway started should be eligible
  *   to win
  * @param hasPublicWinners
  *   <em>Optional</em>. <em>True</em>, if the list of giveaway winners will be visible to everyone
  * @param prizeDescription
  *   <em>Optional</em>. Description of additional giveaway prize
  * @param countryCodes
  *   <em>Optional</em>. A list of two-letter <a href="https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2">ISO 3166-1
  *   alpha-2</a> country codes indicating the countries from which eligible users for the giveaway must come. If empty,
  *   then all users can participate in the giveaway. Users with a phone number that was bought on Fragment can always
  *   participate in giveaways.
  * @param prizeStarCount
  *   <em>Optional</em>. The number of Telegram Stars to be split between giveaway winners; for Telegram Star giveaways
  *   only
  * @param premiumSubscriptionMonthCount
  *   <em>Optional</em>. The number of months the Telegram Premium subscription won from the giveaway will be active
  *   for; for Telegram Premium giveaways only
  */
final case class Giveaway(
  winnersSelectionDate: Long,
  winnerCount: Int,
  chats: List[Chat] = List.empty,
  onlyNewMembers: Option[Boolean] = Option.empty,
  hasPublicWinners: Option[Boolean] = Option.empty,
  prizeDescription: Option[String] = Option.empty,
  countryCodes: List[String] = List.empty,
  prizeStarCount: Option[Int] = Option.empty,
  premiumSubscriptionMonthCount: Option[Int] = Option.empty
)
