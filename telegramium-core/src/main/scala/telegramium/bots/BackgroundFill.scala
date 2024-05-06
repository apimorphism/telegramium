package telegramium.bots

sealed trait BackgroundFill {}

/** The background is a gradient fill.
  *
  * @param topColor
  *   Top color of the gradient in the RGB24 format
  * @param bottomColor
  *   Bottom color of the gradient in the RGB24 format
  * @param rotationAngle
  *   Clockwise rotation angle of the background fill in degrees; 0-359
  */
final case class BackgroundFillGradient(topColor: Int, bottomColor: Int, rotationAngle: Int) extends BackgroundFill

/** The background is a freeform gradient that rotates after every message in the chat.
  *
  * @param colors
  *   A list of the 3 or 4 base colors that are used to generate the freeform gradient in the RGB24 format
  */
final case class BackgroundFillFreeformGradient(colors: List[Int] = List.empty) extends BackgroundFill

/** The background is filled using the selected color.
  *
  * @param color
  *   The color of the background fill in the RGB24 format
  */
final case class BackgroundFillSolid(color: Int) extends BackgroundFill
