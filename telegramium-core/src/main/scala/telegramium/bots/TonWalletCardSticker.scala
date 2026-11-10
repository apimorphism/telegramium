package telegramium.bots

/** This object describes a sticker on a TON wallet card.
  *
  * @param position
  *   Position of the sticker on the card
  * @param nft
  *   The NFT shown as the sticker
  */
final case class TonWalletCardSticker(position: AreaPosition, nft: TonNft)
