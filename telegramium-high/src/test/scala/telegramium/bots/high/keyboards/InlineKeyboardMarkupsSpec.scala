package telegramium.bots.high.keyboards

import org.scalatest.funsuite.AnyFunSuite

import telegramium.bots.InlineKeyboardButton
import telegramium.bots.InlineKeyboardMarkup

class InlineKeyboardMarkupsSpec extends AnyFunSuite {

  test("singleButton should create an inline keyboard markup with one button") {
    val button = InlineKeyboardButtons.url("Button", "https://example.com")
    val markup = InlineKeyboardMarkups.singleButton(button)
    assert(markup == InlineKeyboardMarkup(List(List(button))))
  }

  test("singleRow should create an inline keyboard markup with multiple buttons on a single row") {
    val row = List(
      InlineKeyboardButtons.url("Button 1", "https://example.com"),
      InlineKeyboardButtons.url("Button 2", "https://example.com")
    )
    val markup = InlineKeyboardMarkups.singleRow(row)
    assert(markup == InlineKeyboardMarkup(List(row)))
  }

  test("singleRow (varargs) should create an inline keyboard markup with multiple buttons on a single row") {
    val button1 = InlineKeyboardButtons.url("Button 1", "https://example.com")
    val button2 = InlineKeyboardButtons.url("Button 2", "https://example.com")
    val markup  = InlineKeyboardMarkups.singleRow(button1, button2)
    assert(markup == InlineKeyboardMarkup(List(List(button1, button2))))
  }

  test("singleColumn should create an inline keyboard markup with multiple buttons on a single column") {
    val column = List(
      InlineKeyboardButtons.url("Button 1", "https://example.com"),
      InlineKeyboardButtons.url("Button 2", "https://example.com")
    )
    val markup = InlineKeyboardMarkups.singleColumn(column)
    assert(markup == InlineKeyboardMarkup(List(List(column.head), List(column.tail.head))))
  }

  test("singleColumn (varargs) should create an inline keyboard markup with multiple buttons on a single column") {
    val button1 = InlineKeyboardButtons.url("Button 1", "https://example.com")
    val button2 = InlineKeyboardButtons.url("Button 2", "https://example.com")
    val markup  = InlineKeyboardMarkups.singleColumn(button1, button2)
    assert(markup == InlineKeyboardMarkup(List(List(button1), List(button2))))
  }

}
