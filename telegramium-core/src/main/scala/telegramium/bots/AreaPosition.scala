package telegramium.bots

/** Describes the position of a rectangle area on a story media or a TON wallet card.
  *
  * @param xPercentage
  *   The abscissa of the area's center, as a percentage of the media or the card width
  * @param yPercentage
  *   The ordinate of the area's center, as a percentage of the media or the card height
  * @param widthPercentage
  *   The width of the area's rectangle, as a percentage of the media or the card width
  * @param heightPercentage
  *   The height of the area's rectangle, as a percentage of the media or the card height
  * @param rotationAngle
  *   The clockwise rotation angle of the rectangle, in degrees; 0-360
  * @param cornerRadiusPercentage
  *   The radius of the rectangle corner rounding, as a percentage of the media or the card width
  */
final case class AreaPosition(
  xPercentage: Float,
  yPercentage: Float,
  widthPercentage: Float,
  heightPercentage: Float,
  rotationAngle: Float,
  cornerRadiusPercentage: Float
)
