package telegramium.bots

/** This object describes the types of gifts that can be gifted to a user or a chat.
  *
  * @param unlimitedGifts
  *   <em>True</em>, if unlimited regular gifts are accepted
  * @param limitedGifts
  *   <em>True</em>, if limited regular gifts are accepted
  * @param uniqueGifts
  *   <em>True</em>, if unique gifts or gifts that can be upgraded to unique for free are accepted
  * @param premiumSubscription
  *   <em>True</em>, if a Telegram Premium subscription is accepted
  * @param giftsFromChannels
  *   <em>True</em>, if transfers of unique gifts from channels are accepted
  */
final case class AcceptedGiftTypes(
  unlimitedGifts: Boolean,
  limitedGifts: Boolean,
  uniqueGifts: Boolean,
  premiumSubscription: Boolean,
  giftsFromChannels: Boolean
)
