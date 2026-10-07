package telegramium.bots.high.keyboards

import telegramium.bots.InlineKeyboardButton
import telegramium.bots.InlineKeyboardMarkup

object InlineKeyboardMarkups {

  /** Creates an inline keyboard markup with one button
    */
  def singleButton(
    button: InlineKeyboardButton,
    forceReply: Option[Boolean] = Option.empty
  ): InlineKeyboardMarkup =
    InlineKeyboardMarkup(List(List(button)), forceReply = forceReply)

  /** Creates an inline keyboard markup with multiple buttons on a single row
    */
  def singleRow(
    row: List[InlineKeyboardButton],
    forceReply: Option[Boolean] = Option.empty
  ): InlineKeyboardMarkup =
    InlineKeyboardMarkup(List(row), forceReply = forceReply)

  /** Creates an inline keyboard markup with multiple buttons on a single row
    */
  def singleRow(firstButton: InlineKeyboardButton, buttons: InlineKeyboardButton*): InlineKeyboardMarkup =
    InlineKeyboardMarkup(List(firstButton :: buttons.toList))

  /** Creates an inline keyboard markup with multiple buttons on a single column
    */
  def singleColumn(
    column: List[InlineKeyboardButton],
    forceReply: Option[Boolean] = Option.empty
  ): InlineKeyboardMarkup =
    InlineKeyboardMarkup(column.map(List(_)), forceReply = forceReply)

  /** Creates an inline keyboard markup with multiple buttons on a single column
    */
  def singleColumn(firstButton: InlineKeyboardButton, buttons: InlineKeyboardButton*): InlineKeyboardMarkup =
    InlineKeyboardMarkup((firstButton :: buttons.toList).map(List(_)))

}
