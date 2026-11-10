package telegramium.bots

/** This object represents an NFT in the TON blockchain.
  *
  * @param address
  *   Address of the NFT
  * @param index
  *   Index of the NFT among all NFTs from the collection as a decimal number
  * @param name
  *   Name of the NFT
  * @param description
  *   Description of the NFT
  * @param collectionAddress
  *   <em>Optional</em>. Address of the collection to which the NFT belongs; omitted if unknown
  * @param attributes
  *   <em>Optional</em>. Attributes of the NFT
  * @param gift
  *   <em>Optional</em>. The unique gift corresponding to the NFT
  */
final case class TonNft(
  address: String,
  index: String,
  name: String,
  description: String,
  collectionAddress: Option[String] = Option.empty,
  attributes: List[TonNftAttribute] = List.empty,
  gift: Option[UniqueGift] = Option.empty
)
