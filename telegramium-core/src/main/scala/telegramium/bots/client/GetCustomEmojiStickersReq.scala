package telegramium.bots.client

/** @param customEmojiIds
  *   A JSON-serialized list of custom emoji identifiers. At most 200 custom emoji identifiers can be specified.
  */
final case class GetCustomEmojiStickersReq(customEmojiIds: List[String] = List.empty)
