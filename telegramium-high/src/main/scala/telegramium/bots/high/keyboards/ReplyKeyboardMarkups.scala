package telegramium.bots.high.keyboards

import telegramium.bots.{KeyboardButton, ReplyKeyboardMarkup}

object ReplyKeyboardMarkups {
  /**
   * Creates a reply keyboard markup with one button
   */
  def singleButton(
    button: KeyboardButton,
    resizeKeyboard: Option[Boolean] = Option.empty,
    oneTimeKeyboard: Option[Boolean] = Option.empty,
    selective: Option[Boolean] = Option.empty
  ): ReplyKeyboardMarkup =
    ReplyKeyboardMarkup(
      keyboard = List(List(button)),
      resizeKeyboard = resizeKeyboard,
      oneTimeKeyboard = oneTimeKeyboard,
      selective = selective
    )

  /**
   * Creates a reply keyboard markup with multiple buttons on a single row
   */
  def singleRow(
    row: List[KeyboardButton],
    resizeKeyboard: Option[Boolean] = Option.empty,
    oneTimeKeyboard: Option[Boolean] = Option.empty,
    selective: Option[Boolean] = Option.empty
  ): ReplyKeyboardMarkup =
    ReplyKeyboardMarkup(
      keyboard = List(row),
      resizeKeyboard = resizeKeyboard,
      oneTimeKeyboard = oneTimeKeyboard,
      selective = selective
    )

  /**
   * Creates a reply keyboard markup with multiple buttons on a single column
   */
  def singleColumn(
    column: List[KeyboardButton],
    resizeKeyboard: Option[Boolean] = Option.empty,
    oneTimeKeyboard: Option[Boolean] = Option.empty,
    selective: Option[Boolean] = Option.empty
  ): ReplyKeyboardMarkup =
    ReplyKeyboardMarkup(
      keyboard = column.map(List(_)),
      resizeKeyboard = resizeKeyboard,
      oneTimeKeyboard = oneTimeKeyboard,
      selective = selective
    )
}
