package telegramium.bots.client

import telegramium.bots.InputSticker

/** @param userId
  *   User identifier of the sticker set owner
  * @param name
  *   Sticker set name
  * @param oldSticker
  *   File identifier of the replaced sticker
  * @param sticker
  *   A JSON-serialized object with information about the added sticker. If exactly the same sticker had already been
  *   added to the set, then the set remains unchanged.
  */
final case class ReplaceStickerInSetReq(userId: Long, name: String, oldSticker: String, sticker: InputSticker)
