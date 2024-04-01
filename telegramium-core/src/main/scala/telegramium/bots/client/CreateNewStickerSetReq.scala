package telegramium.bots.client

import telegramium.bots.InputSticker

/** @param userId
  *   User identifier of created sticker set owner
  * @param name
  *   Short name of sticker set, to be used in t.me/addstickers/ URLs (e.g., animals). Can contain only English letters,
  *   digits and underscores. Must begin with a letter, can't contain consecutive underscores and must end in
  *   "_by_<bot_username>". <bot_username> is case insensitive. 1-64 characters.
  * @param title
  *   Sticker set title, 1-64 characters
  * @param stickers
  *   A JSON-serialized list of 1-50 initial stickers to be added to the sticker set
  * @param stickerType
  *   Type of stickers in the set, pass “regular”, “mask”, or “custom_emoji”. By default, a regular sticker set is
  *   created.
  * @param needsRepainting
  *   Pass True if stickers in the sticker set must be repainted to the color of text when used in messages, the accent
  *   color if used as emoji status, white on chat photos, or another appropriate color based on context; for custom
  *   emoji sticker sets only
  */
final case class CreateNewStickerSetReq(
  userId: Long,
  name: String,
  title: String,
  stickers: List[InputSticker] = List.empty,
  stickerType: Option[String] = Option.empty,
  needsRepainting: Option[Boolean] = Option.empty
)
