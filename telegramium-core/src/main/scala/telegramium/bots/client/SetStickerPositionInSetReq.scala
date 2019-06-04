package telegramium.bots.client

final case class SetStickerPositionInSetReq(
                                            /** File identifier of the sticker*/
                                            sticker: String,
                                            /** New sticker position in the set, zero-based*/
                                            position: Int)
