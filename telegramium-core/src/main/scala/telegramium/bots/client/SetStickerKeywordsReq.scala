package telegramium.bots.client

/** @param sticker
  *   File identifier of the sticker
  * @param keywords
  *   A JSON-serialized list of 0-20 search keywords for the sticker with total length of up to 64 characters
  */
final case class SetStickerKeywordsReq(sticker: String, keywords: List[String] = List.empty)
