package telegramium.bots.high.keyboards

import telegramium.bots.InlineKeyboardButton
import telegramium.bots.InlineKeyboardMarkup

object InlineKeyboardMarkups {

  /** Creates an inline keyboard markup with one button
    */
  def singleButton(button: InlineKeyboardButton): InlineKeyboardMarkup = InlineKeyboardMarkup(List(List(button)))

  /** Creates an inline keyboard markup with multiple buttons on a single row
    */
  def singleRow(row: List[InlineKeyboardButton]): InlineKeyboardMarkup = InlineKeyboardMarkup(List(row))

  /** Creates an inline keyboard markup with multiple buttons on a single row
    */
  def singleRow(firstButton: InlineKeyboardButton, buttons: InlineKeyboardButton*): InlineKeyboardMarkup =
    InlineKeyboardMarkup(List(firstButton :: buttons.toList))

  /** Creates an inline keyboard markup with multiple buttons on a single column
    */
  def singleColumn(column: List[InlineKeyboardButton]): InlineKeyboardMarkup = InlineKeyboardMarkup(column.map(List(_)))

  /** Creates an inline keyboard markup with multiple buttons on a single column
    */
  def singleColumn(firstButton: InlineKeyboardButton, buttons: InlineKeyboardButton*): InlineKeyboardMarkup =
    InlineKeyboardMarkup((firstButton :: buttons.toList).map(List(_)))

}
