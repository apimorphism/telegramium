package telegramium.bots.client

/** @param sticker
  *   File identifier of the sticker
  * @param emojiList
  *   A JSON-serialized list of 1-20 emoji associated with the sticker
  */
final case class SetStickerEmojiListReq(sticker: String, emojiList: List[String] = List.empty)
