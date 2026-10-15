package telegramium.bots

/** This object describes a unique gift that was upgraded from a regular gift.
  *
  * @param giftId
  *   Identifier of the regular gift from which the gift was upgraded
  * @param baseName
  *   Human-readable name of the regular gift from which this unique gift was upgraded
  * @param name
  *   Unique name of the gift. This name can be used in <code>https://t.me/nft/...</code> links and story areas.
  * @param number
  *   Unique number of the upgraded gift among gifts upgraded from the same regular gift
  * @param model
  *   Model of the gift
  * @param symbol
  *   Symbol of the gift
  * @param backdrop
  *   Backdrop of the gift
  * @param isPremium
  *   <em>Optional</em>. <em>True</em>, if the original regular gift was exclusively purchaseable by Telegram Premium
  *   subscribers
  * @param isBurned
  *   <em>Optional</em>. <em>True</em>, if the gift was used to craft another gift and isn't available anymore
  * @param isFromBlockchain
  *   <em>Optional</em>. <em>True</em>, if the gift is assigned from the TON blockchain and can't be resold or
  *   transferred in Telegram
  * @param colors
  *   <em>Optional</em>. The color scheme that can be used by the gift's owner for the chat's name, replies to messages
  *   and link previews; for business account gifts and gifts that are currently on sale only
  * @param publisherChat
  *   <em>Optional</em>. Information about the chat that published the gift
  */
final case class UniqueGift(
  giftId: String,
  baseName: String,
  name: String,
  number: Int,
  model: UniqueGiftModel,
  symbol: UniqueGiftSymbol,
  backdrop: UniqueGiftBackdrop,
  isPremium: Option[Boolean] = Option.empty,
  isBurned: Option[Boolean] = Option.empty,
  isFromBlockchain: Option[Boolean] = Option.empty,
  colors: Option[UniqueGiftColors] = Option.empty,
  publisherChat: Option[Chat] = Option.empty
)
