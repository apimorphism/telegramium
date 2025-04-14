package telegramium.bots

/** This object describes the model of a unique gift.
  *
  * @param name
  *   Name of the model
  * @param sticker
  *   The sticker that represents the unique gift
  * @param rarityPerMille
  *   The number of unique gifts that receive this model for every 1000 gifts upgraded
  */
final case class UniqueGiftModel(name: String, sticker: Sticker, rarityPerMille: Int)
