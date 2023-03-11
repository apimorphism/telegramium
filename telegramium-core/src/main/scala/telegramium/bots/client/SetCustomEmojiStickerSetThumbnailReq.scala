package telegramium.bots.client

/** @param name
  *   Sticker set name
  * @param customEmojiId
  *   Custom emoji identifier of a sticker from the sticker set; pass an empty string to drop the thumbnail and use the
  *   first sticker as the thumbnail.
  */
final case class SetCustomEmojiStickerSetThumbnailReq(name: String, customEmojiId: Option[String] = Option.empty)
