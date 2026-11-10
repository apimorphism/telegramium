package telegramium.bots

/** This object describes a TON wallet card, which is shown on the profile page of a user to everyone.
  *
  * @param address
  *   Address of the wallet
  * @param amount
  *   Balance of the wallet, in the <em>smallest units</em> of the cryptocurrency, as a decimal number
  * @param stickers
  *   Stickers placed on the card
  * @param backgroundNftAddress
  *   <em>Optional</em>. Address of the NFT of one of the stickers, whose colors must be used for the card background;
  *   omitted if the default background must be used
  */
final case class TonWalletCard(
  address: String,
  amount: String,
  stickers: List[TonWalletCardSticker] = List.empty,
  backgroundNftAddress: Option[String] = Option.empty
)
