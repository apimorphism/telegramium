package telegramium.bots

/** This object describes the background of a gift.
  *
  * @param centerColor
  *   Center color of the background in RGB format
  * @param edgeColor
  *   Edge color of the background in RGB format
  * @param textColor
  *   Text color of the background in RGB format
  */
final case class GiftBackground(centerColor: Int, edgeColor: Int, textColor: Int)
