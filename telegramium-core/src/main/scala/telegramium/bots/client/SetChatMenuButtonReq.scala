package telegramium.bots.client

import telegramium.bots.MenuButton

/** @param chatId
  *   Unique identifier for the target private chat. If not specified, default bot's menu button will be changed
  * @param menuButton
  *   A JSON-serialized object for the new bot's menu button. Defaults to MenuButtonDefault
  */
final case class SetChatMenuButtonReq(chatId: Option[Int] = Option.empty, menuButton: Option[MenuButton] = Option.empty)
