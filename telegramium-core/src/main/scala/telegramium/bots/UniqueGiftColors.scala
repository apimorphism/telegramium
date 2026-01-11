package telegramium.bots

/** This object contains information about the color scheme for a user's name, message replies and link previews based
  * on a unique gift.
  *
  * @param modelCustomEmojiId
  *   Custom emoji identifier of the unique gift's model
  * @param symbolCustomEmojiId
  *   Custom emoji identifier of the unique gift's symbol
  * @param lightThemeMainColor
  *   Main color used in light themes; RGB format
  * @param darkThemeMainColor
  *   Main color used in dark themes; RGB format
  * @param lightThemeOtherColors
  *   List of 1-3 additional colors used in light themes; RGB format
  * @param darkThemeOtherColors
  *   List of 1-3 additional colors used in dark themes; RGB format
  */
final case class UniqueGiftColors(
  modelCustomEmojiId: String,
  symbolCustomEmojiId: String,
  lightThemeMainColor: Int,
  darkThemeMainColor: Int,
  lightThemeOtherColors: List[Int] = List.empty,
  darkThemeOtherColors: List[Int] = List.empty
)
