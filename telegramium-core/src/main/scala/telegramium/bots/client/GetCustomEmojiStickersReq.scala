package telegramium.bots.client

/** @param customEmojiIds
  *   List of custom emoji identifiers. At most 200 custom emoji identifiers can be specified.
  */
final case class GetCustomEmojiStickersReq(customEmojiIds: List[String] = List.empty)
