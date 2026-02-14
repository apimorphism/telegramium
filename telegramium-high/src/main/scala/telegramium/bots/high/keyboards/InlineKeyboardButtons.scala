package telegramium.bots.high.keyboards

import telegramium.bots.CallbackGame
import telegramium.bots.InlineKeyboardButton
import telegramium.bots.LoginUrl
import telegramium.bots.SwitchInlineQueryChosenChat
import telegramium.bots.WebAppInfo

object InlineKeyboardButtons {

  /** Creates an inline keyboard button that sends a callback query to bot when pressed
    */
  def callbackData(
    text: String,
    callbackData: String,
    iconCustomEmojiId: Option[String] = Option.empty,
    style: Option[String] = Option.empty
  ): InlineKeyboardButton =
    InlineKeyboardButton(text, callbackData = Some(callbackData), iconCustomEmojiId = iconCustomEmojiId, style = style)

  /** Creates an inline keyboard button that opens a HTTP url when pressed
    */
  def url(
    text: String,
    url: String,
    iconCustomEmojiId: Option[String] = Option.empty,
    style: Option[String] = Option.empty
  ): InlineKeyboardButton =
    InlineKeyboardButton(text, url = Some(url), iconCustomEmojiId = iconCustomEmojiId, style = style)

  /** Creates an inline keyboard button that opens a HTTP URL to automatically authorize the user
    */
  def loginUrl(
    text: String,
    loginUrl: LoginUrl,
    iconCustomEmojiId: Option[String] = Option.empty,
    style: Option[String] = Option.empty
  ): InlineKeyboardButton =
    InlineKeyboardButton(text, loginUrl = Some(loginUrl), iconCustomEmojiId = iconCustomEmojiId, style = style)

  /** Creates an inline keyboard button. Pressing the button will prompt the user to select one of their chats, open
    * that chat and insert the bot's username and the specified inline query in the input field.
    */
  def switchInlineQuery(
    text: String,
    query: String,
    iconCustomEmojiId: Option[String] = Option.empty,
    style: Option[String] = Option.empty
  ): InlineKeyboardButton =
    InlineKeyboardButton(text, switchInlineQuery = Some(query), iconCustomEmojiId = iconCustomEmojiId, style = style)

  /** Creates an inline keyboard button. Pressing the button will insert the bot's username and the specified inline
    * query in the current chat's input field.
    */
  def switchInlineQueryCurrentChat(
    text: String,
    query: String,
    iconCustomEmojiId: Option[String] = Option.empty,
    style: Option[String] = Option.empty
  ): InlineKeyboardButton =
    InlineKeyboardButton(
      text,
      switchInlineQueryCurrentChat = Some(query),
      iconCustomEmojiId = iconCustomEmojiId,
      style = style
    )

  /** Creates an inline keyboard button. Pressing the button will prompt the user to select one of their chats of the
    * specified type, open that chat and insert the bot's username and the specified inline query in the input field.
    */
  def switchInlineQueryChosenChat(
    text: String,
    switchInlineQueryChosenChat: SwitchInlineQueryChosenChat,
    iconCustomEmojiId: Option[String] = Option.empty,
    style: Option[String] = Option.empty
  ): InlineKeyboardButton =
    InlineKeyboardButton(
      text,
      switchInlineQueryChosenChat = Some(switchInlineQueryChosenChat),
      iconCustomEmojiId = iconCustomEmojiId,
      style = style
    )

  /** Creates an inline keyboard button. Pressing the button will launch the game.
    */
  def callbackGame(
    text: String,
    iconCustomEmojiId: Option[String] = Option.empty,
    style: Option[String] = Option.empty
  ): InlineKeyboardButton =
    InlineKeyboardButton(text, callbackGame = Some(CallbackGame), iconCustomEmojiId = iconCustomEmojiId, style = style)

  /** Creates an inline keyboard button for a Pay button
    */
  def pay(
    text: String,
    iconCustomEmojiId: Option[String] = Option.empty,
    style: Option[String] = Option.empty
  ): InlineKeyboardButton =
    InlineKeyboardButton(text, pay = Some(true), iconCustomEmojiId = iconCustomEmojiId, style = style)

  /** Creates an inline keyboard button. Pressing the button will launch the described Web App.
    */
  def webApp(
    text: String,
    webApp: WebAppInfo,
    iconCustomEmojiId: Option[String] = Option.empty,
    style: Option[String] = Option.empty
  ): InlineKeyboardButton =
    InlineKeyboardButton(text, webApp = Some(webApp), iconCustomEmojiId = iconCustomEmojiId, style = style)

}
