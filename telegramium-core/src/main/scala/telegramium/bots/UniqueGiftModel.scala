package telegramium.bots

/** This object describes the model of a unique gift.
  *
  * @param name
  *   Name of the model
  * @param sticker
  *   The sticker that represents the unique gift
  * @param rarityPerMille
  *   The number of unique gifts that receive this model for every 1000 gift upgrades. Always 0 for crafted gifts.
  * @param rarity
  *   Optional. Rarity of the model if it is a crafted model. Currently, can be “uncommon”, “rare”, “epic”, or
  *   “legendary”.
  */
final case class UniqueGiftModel(
  name: String,
  sticker: Sticker,
  rarityPerMille: Int,
  rarity: Option[String] = Option.empty
)
