package telegramium.bots.high.keyboards

import org.scalatest.funsuite.AnyFunSuite

import telegramium.bots.DisabledButton
import telegramium.bots.InlineKeyboardButton

class InlineKeyboardButtonsSpec extends AnyFunSuite {

  test("callbackData should not set disabled") {
    val button = InlineKeyboardButtons.callbackData("Button", "data")
    assert(button.disabled == Option.empty)
  }

  test("disabled should create a disabled button") {
    val button = InlineKeyboardButtons.disabled("Button")
    assert(button == InlineKeyboardButton("Button", disabled = Some(DisabledButton)))
  }

}
