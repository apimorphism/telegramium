package telegramium.bots.client

import telegramium.bots.KeyboardButton

/** @param userId
  *   Unique identifier of the target user that can use the button
  * @param button
  *   A JSON-serialized object describing the button to be saved. The button must be of the type request_users,
  *   request_chat, or request_managed_bot
  */
final case class SavePreparedKeyboardButtonReq(userId: Long, button: KeyboardButton)
