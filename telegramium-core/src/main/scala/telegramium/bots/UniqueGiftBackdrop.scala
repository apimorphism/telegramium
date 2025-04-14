package telegramium.bots

/** This object describes the backdrop of a unique gift.
  *
  * @param name
  *   Name of the backdrop
  * @param colors
  *   Colors of the backdrop
  * @param rarityPerMille
  *   The number of unique gifts that receive this backdrop for every 1000 gifts upgraded
  */
final case class UniqueGiftBackdrop(name: String, colors: UniqueGiftBackdropColors, rarityPerMille: Int)
