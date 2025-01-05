package telegramium.bots

sealed trait BackgroundType {}

/** The background is a wallpaper in the JPEG format.
  *
  * @param document
  *   Document with the wallpaper
  * @param darkThemeDimming
  *   Dimming of the background in dark themes, as a percentage; 0-100
  * @param isBlurred
  *   Optional. True, if the wallpaper is downscaled to fit in a 450x450 square and then box-blurred with radius 12
  * @param isMoving
  *   Optional. True, if the background moves slightly when the device is tilted
  */
final case class BackgroundTypeWallpaper(
  document: Document,
  darkThemeDimming: Int,
  isBlurred: Option[Boolean] = Option.empty,
  isMoving: Option[Boolean] = Option.empty
) extends BackgroundType

/** The background is a .PNG or .TGV (gzipped subset of SVG with MIME type “application/x-tgwallpattern”) pattern to be
  * combined with the background fill chosen by the user.
  *
  * @param document
  *   Document with the pattern
  * @param fill
  *   The background fill that is combined with the pattern
  * @param intensity
  *   Intensity of the pattern when it is shown above the filled background; 0-100
  * @param isInverted
  *   Optional. True, if the background fill must be applied only to the pattern itself. All other pixels are black in
  *   this case. For dark themes only
  * @param isMoving
  *   Optional. True, if the background moves slightly when the device is tilted
  */
final case class BackgroundTypePattern(
  document: Document,
  fill: iozhik.OpenEnum[BackgroundFill],
  intensity: Int,
  isInverted: Option[Boolean] = Option.empty,
  isMoving: Option[Boolean] = Option.empty
) extends BackgroundType

/** The background is taken directly from a built-in chat theme.
  *
  * @param themeName
  *   Name of the chat theme, which is usually an emoji
  */
final case class BackgroundTypeChatTheme(themeName: String) extends BackgroundType

/** The background is automatically filled based on the selected colors.
  *
  * @param fill
  *   The background fill
  * @param darkThemeDimming
  *   Dimming of the background in dark themes, as a percentage; 0-100
  */
final case class BackgroundTypeFill(fill: iozhik.OpenEnum[BackgroundFill], darkThemeDimming: Int) extends BackgroundType
