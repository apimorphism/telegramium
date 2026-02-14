package telegramium.bots.high.keyboards

import telegramium.bots.KeyboardButton
import telegramium.bots.KeyboardButtonPollType
import telegramium.bots.KeyboardButtonRequestChat
import telegramium.bots.KeyboardButtonRequestUsers
import telegramium.bots.WebAppInfo

object KeyboardButtons {

  /** Text will be sent to the bot as a message when the button is pressed.
    */
  def text(
    text: String,
    iconCustomEmojiId: Option[String] = Option.empty,
    style: Option[String] = Option.empty
  ): KeyboardButton = KeyboardButton(text, iconCustomEmojiId = iconCustomEmojiId, style = style)

  /** The user's phone number will be sent as a contact when the button is pressed.
    */
  def requestContact(
    text: String,
    iconCustomEmojiId: Option[String] = Option.empty,
    style: Option[String] = Option.empty
  ): KeyboardButton =
    KeyboardButton(text, requestContact = Some(true), iconCustomEmojiId = iconCustomEmojiId, style = style)

  /** The user's current location will be sent when the button is pressed
    */
  def requestLocation(
    text: String,
    iconCustomEmojiId: Option[String] = Option.empty,
    style: Option[String] = Option.empty
  ): KeyboardButton =
    KeyboardButton(text, requestLocation = Some(true), iconCustomEmojiId = iconCustomEmojiId, style = style)

  /** The user will be asked to create a poll and send it to the bot when the button is pressed
    */
  def requestPoll(
    text: String,
    `type`: KeyboardButtonPollType,
    iconCustomEmojiId: Option[String] = Option.empty,
    style: Option[String] = Option.empty
  ): KeyboardButton =
    KeyboardButton(text, requestPoll = Some(`type`), iconCustomEmojiId = iconCustomEmojiId, style = style)

  /** Pressing the button will open a list of suitable users. Tapping on any user will send their identifier to the bot
    * in a “user_shared” service message.
    */
  def requestUsers(
    text: String,
    requestUsers: KeyboardButtonRequestUsers,
    iconCustomEmojiId: Option[String] = Option.empty,
    style: Option[String] = Option.empty
  ) =
    KeyboardButton(text, requestUsers = Some(requestUsers), iconCustomEmojiId = iconCustomEmojiId, style = style)

  /** Pressing the button will open a list of suitable chats. Tapping on a chat will send its identifier to the bot in a
    * “chat_shared” service message.
    */
  def requestChat(
    text: String,
    requestChat: KeyboardButtonRequestChat,
    iconCustomEmojiId: Option[String] = Option.empty,
    style: Option[String] = Option.empty
  ) =
    KeyboardButton(text, requestChat = Some(requestChat), iconCustomEmojiId = iconCustomEmojiId, style = style)

  /** The described Web App will be launched when the button is pressed.
    */
  def webApp(
    text: String,
    webApp: WebAppInfo,
    iconCustomEmojiId: Option[String] = Option.empty,
    style: Option[String] = Option.empty
  ) =
    KeyboardButton(text, webApp = Some(webApp), iconCustomEmojiId = iconCustomEmojiId, style = style)

}
