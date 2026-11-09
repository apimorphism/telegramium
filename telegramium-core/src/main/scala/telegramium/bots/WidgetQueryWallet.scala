package telegramium.bots

/** This object describes a TON wallet of the user, which is used in a query from a widget.
  *
  * @param address
  *   Address of the wallet
  * @param amount
  *   Balance of the wallet, in the <em>smallest units</em> of the cryptocurrency, as a decimal number
  * @param publicKey
  *   <em>Optional</em>. Hex-encoded public key of the wallet; omitted if unknown
  * @param stateInit
  *   <em>Optional</em>. Base64-encoded serialized StateInit of the wallet contract; omitted if and only if
  *   <em>public_key</em> is omitted
  */
final case class WidgetQueryWallet(
  address: String,
  amount: String,
  publicKey: Option[String] = Option.empty,
  stateInit: Option[String] = Option.empty
)
