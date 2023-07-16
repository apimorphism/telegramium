package telegramium.bots.high.keyboards

import telegramium.bots.KeyboardButton
import telegramium.bots.KeyboardButtonPollType
import telegramium.bots.KeyboardButtonRequestChat
import telegramium.bots.KeyboardButtonRequestUser
import telegramium.bots.WebAppInfo

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

  /** Pressing the button will open a list of suitable users. Tapping on any user will send their identifier to the bot
    * in a “user_shared” service message.
    */
  def requestUser(text: String, requestUser: KeyboardButtonRequestUser) =
    KeyboardButton(text, requestUser = Some(requestUser))

  /** Pressing the button will open a list of suitable chats. Tapping on a chat will send its identifier to the bot in a
    * “chat_shared” service message.
    */
  def requestChat(text: String, requestChat: KeyboardButtonRequestChat) =
    KeyboardButton(text, requestChat = Some(requestChat))

  /** The described Web App will be launched when the button is pressed.
    */
  def webApp(text: String, webApp: WebAppInfo) =
    KeyboardButton(text, webApp = Some(webApp))

}
