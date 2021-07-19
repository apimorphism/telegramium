package telegramium.bots.high.keyboards

import telegramium.bots.{CallbackGame, InlineKeyboardButton, LoginUrl}

object InlineKeyboardButtons {

  /** Creates an inline keyboard button that sends a callback query to bot when pressed
    */
  def callbackData(text: String, callbackData: String): InlineKeyboardButton =
    InlineKeyboardButton(text, callbackData = Some(callbackData))

  /** Creates an inline keyboard button that opens a HTTP url when pressed
    */
  def url(text: String, url: String): InlineKeyboardButton =
    InlineKeyboardButton(text, url = Some(url))

  /** Creates an inline keyboard button that opens a HTTP URL to automatically authorize the user
    */
  def loginUrl(text: String, loginUrl: LoginUrl): InlineKeyboardButton =
    InlineKeyboardButton(text, loginUrl = Some(loginUrl))

  /** Creates an inline keyboard button. Pressing the button will prompt the user to select one of their chats, open
    * that chat and insert the bot's username and the specified inline query in the input field.
    */
  def switchInlineQuery(text: String, query: String): InlineKeyboardButton =
    InlineKeyboardButton(text, switchInlineQuery = Some(query))

  /** Creates an inline keyboard button. Pressing the button will insert the bot's username and the specified inline
    * query in the current chat's input field.
    */
  def switchInlineQueryCurrentChat(text: String, query: String): InlineKeyboardButton =
    InlineKeyboardButton(text, switchInlineQueryCurrentChat = Some(query))

  /** Creates an inline keyboard button. Pressing the button will launch the game.
    */
  def callbackGame(text: String): InlineKeyboardButton =
    InlineKeyboardButton(text, callbackGame = Some(CallbackGame))

  /** Creates an inline keyboard button for a Pay button
    */
  def pay(text: String): InlineKeyboardButton =
    InlineKeyboardButton(text, pay = Some(true))

}
