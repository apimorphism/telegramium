package telegramium.bots.high.keyboards

import org.scalatest.funsuite.AnyFunSuite

import telegramium.bots.DisabledButton

class InlineKeyboardButtonsSpec extends AnyFunSuite {

  test("callbackData should not set disabled by default") {
    val button = InlineKeyboardButtons.callbackData("Button", "data")
    assert(button.disabled == Option.empty)
  }

  test("callbackData should set disabled when requested") {
    val button = InlineKeyboardButtons.callbackData("Button", "data", disabled = true)
    assert(button.disabled == Some(DisabledButton))
  }

  test("url should set disabled when requested") {
    val button = InlineKeyboardButtons.url("Button", "https://example.com", disabled = true)
    assert(button.disabled == Some(DisabledButton))
  }

}
