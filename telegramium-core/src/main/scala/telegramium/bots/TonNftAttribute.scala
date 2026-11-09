package telegramium.bots

/** This object represents an attribute of an NFT in the TON blockchain.
  *
  * @param traitType
  *   Trait type of the attribute
  * @param value
  *   Value of the attribute
  */
final case class TonNftAttribute(traitType: String, value: String)
