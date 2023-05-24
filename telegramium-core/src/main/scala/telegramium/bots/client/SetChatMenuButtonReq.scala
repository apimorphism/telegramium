package telegramium.bots.client

import telegramium.bots.MenuButton

/** @param chatId
  *   Unique identifier for the target private chat. If not specified, default bot's menu button will be changed
  * @param menuButton
  *   A JSON-serialized object for the bot's new menu button. Defaults to MenuButtonDefault
  */
final case class SetChatMenuButtonReq(
  chatId: Option[Long] = Option.empty,
  menuButton: Option[MenuButton] = Option.empty
)
