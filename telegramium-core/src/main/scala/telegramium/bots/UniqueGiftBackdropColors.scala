package telegramium.bots

/** This object describes the colors of the backdrop of a unique gift.
  *
  * @param centerColor
  *   The color in the center of the backdrop in RGB format
  * @param edgeColor
  *   The color on the edges of the backdrop in RGB format
  * @param symbolColor
  *   The color to be applied to the symbol in RGB format
  * @param textColor
  *   The color for the text on the backdrop in RGB format
  */
final case class UniqueGiftBackdropColors(centerColor: Int, edgeColor: Int, symbolColor: Int, textColor: Int)
