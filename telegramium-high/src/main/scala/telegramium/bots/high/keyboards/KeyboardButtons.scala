package telegramium.bots.high.keyboards

import telegramium.bots.{KeyboardButton, KeyboardButtonPollType}

object KeyboardButtons {

  /** Text will be sent to the bot as a message when the button is pressed.
    */
  def text(text: String): KeyboardButton = KeyboardButton(text)

  /** The user's phone number will be sent as a contact when the button is pressed.
    */
  def requestContact(text: String): KeyboardButton = KeyboardButton(text, requestContact = Some(true))

  /** The user's current location will be sent when the button is pressed
    */
  def requestLocation(text: String): KeyboardButton = KeyboardButton(text, requestLocation = Some(true))

  /** The user will be asked to create a poll and send it to the bot when the button is pressed
    */
  def requestPoll(text: String, `type`: KeyboardButtonPollType): KeyboardButton =
    KeyboardButton(text, requestPoll = Some(`type`))

}
