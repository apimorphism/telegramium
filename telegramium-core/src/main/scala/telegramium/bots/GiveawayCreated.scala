package telegramium.bots

/** This object represents a service message about the creation of a scheduled giveaway.
  *
  * @param prizeStarCount
  *   Optional. The number of Telegram Stars to be split between giveaway winners; for Telegram Star giveaways only
  */
final case class GiveawayCreated(prizeStarCount: Option[Int] = Option.empty)
