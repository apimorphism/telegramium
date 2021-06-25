package telegramium.bots.client

/**
 * @param sticker File identifier of the sticker
 * @param position New sticker position in the set, zero-based
 */
final case class SetStickerPositionInSetReq(sticker: String, position: Int)
