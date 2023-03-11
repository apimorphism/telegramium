package telegramium.bots.client

import telegramium.bots.InputSticker

/** @param userId
  *   User identifier of sticker set owner
  * @param name
  *   Sticker set name
  * @param sticker
  *   A JSON-serialized object with information about the added sticker. If exactly the same sticker had already been
  *   added to the set, then the set isn't changed.
  */
final case class AddStickerToSetReq(userId: Long, name: String, sticker: InputSticker)
